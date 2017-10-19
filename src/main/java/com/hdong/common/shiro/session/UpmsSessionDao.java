package com.hdong.common.shiro.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hdong.common.base.BasePageResult;
import com.hdong.common.shiro.filter.RequestThreadLocalFilter;
import com.hdong.common.util.RedisUtil;
import com.hdong.common.util.SerializableUtil;
import com.hdong.upms.common.constant.UpmsConstant;

import redis.clients.jedis.Jedis;

/**
 * 基于redis的sessionDao，缓存共享session Created by hdong on 2017/2/23.
 */
public class UpmsSessionDao extends CachingSessionDAO {

    private static Logger _log = LoggerFactory.getLogger(UpmsSessionDao.class);

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        RedisUtil.set(UpmsConstant.SHIRO_SESSION_ID + "_" + sessionId, SerializableUtil.serialize(session), (int) session.getTimeout() / 1000);
        _log.debug("doCreate >>>>> sessionId={}", sessionId);
        RequestThreadLocalFilter.setLocalSession((UpmsSession)session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if(!RequestThreadLocalFilter.isControllerReq()) {
            return null;
        }
        if(RequestThreadLocalFilter.isLocalSession()) {
            //_log.debug("use local session >>>>> sessionId={}", sessionId);
            return RequestThreadLocalFilter.getLocalSession();
        }
        _log.debug("use redis session >>>>> sessionId={}", sessionId);
        String sessionStr = RedisUtil.get(UpmsConstant.SHIRO_SESSION_ID + "_" + sessionId);
        UpmsSession session = (UpmsSession) SerializableUtil.deserialize(sessionStr);
        RequestThreadLocalFilter.setLocalSession(session);
        return session;
    }

    @Override
    protected void doUpdate(Session session) {
        if(RequestThreadLocalFilter.isControllerReq()) {
            UpmsSession upmsSession = (UpmsSession) session;
            UpmsSession cacheUpmsSession = (UpmsSession) doReadSession(session.getId());
            if (null != cacheUpmsSession) {
                upmsSession.setStatus(cacheUpmsSession.getStatus());
                upmsSession.setAttribute(UpmsConstant.FORCE_LOGOUT, cacheUpmsSession.getAttribute(UpmsConstant.FORCE_LOGOUT));
            }
            RedisUtil.set(UpmsConstant.SHIRO_SESSION_ID + "_" + session.getId(), SerializableUtil.serialize(session), (int) session.getTimeout() / 1000);
            // 更新HDONG_UPMS_SERVER_SESSION_ID、HDONG_UPMS_SERVER_CODE过期时间
            _log.debug("doUpdateSessionByRedis >>>>> sessionId={}", session.getId());
        }
    }

    @Override
    protected void doDelete(Session session) {
        String sessionId = session.getId().toString();
        if ("client".equals(UpmsConstant.UPMS_TYPE)) {
            // 删除局部会话和同一code注册的局部会话
            String code = RedisUtil.get(UpmsConstant.CLIENT_SESSION_ID + "_" + sessionId);
            Jedis jedis = RedisUtil.getJedis();
            jedis.del(UpmsConstant.CLIENT_SESSION_ID + "_" + sessionId);
            jedis.srem(UpmsConstant.CLIENT_SESSION_IDS + "_" + code, sessionId);
            jedis.close();
        }
        if ("server".equals(UpmsConstant.UPMS_TYPE)) {
            // 当前全局会话code
            String code = RedisUtil.get(UpmsConstant.SERVER_SESSION_ID + "_" + sessionId);
            // 清除全局会话
            RedisUtil.remove(UpmsConstant.SERVER_SESSION_ID + "_" + sessionId);
            // 清除code校验值
            RedisUtil.remove(UpmsConstant.SERVER_CODE + "_" + code);
            // 清除所有局部会话
            Jedis jedis = RedisUtil.getJedis();
            Set<String> clientSessionIds = jedis.smembers(UpmsConstant.CLIENT_SESSION_IDS + "_" + code);
            for (String clientSessionId : clientSessionIds) {
                jedis.del(UpmsConstant.CLIENT_SESSION_ID + "_" + clientSessionId);
                jedis.srem(UpmsConstant.CLIENT_SESSION_IDS + "_" + code, clientSessionId);
            }
            _log.debug("当前code={}，对应的注册系统个数：{}个", code, jedis.scard(UpmsConstant.CLIENT_SESSION_IDS + "_" + code));
            jedis.close();
            // 维护会话id列表，提供会话分页管理
            RedisUtil.lrem(UpmsConstant.SERVER_SESSION_IDS, 1, sessionId);
        }
        // 删除session
        RedisUtil.remove(UpmsConstant.SHIRO_SESSION_ID + "_" + sessionId);
        _log.debug("doUpdate >>>>> sessionId={}", sessionId);
    }

    /**
     * 获取会话列表
     * 
     * @param offset
     * @param limit
     * @return
     */
    public BasePageResult<Session> getActiveSessions(String username, int offset, int limit) {
        Jedis jedis = RedisUtil.getJedis();
        // 获取在线会话总数
        int total = new Long(jedis.llen(UpmsConstant.SERVER_SESSION_IDS)).intValue();
        // 获取当前页会话详情
        List<String> ids = jedis.lrange(UpmsConstant.SERVER_SESSION_IDS, 0, total);
        List<Session> rows = new ArrayList<Session>();
        for (String id : ids) {
            String session = RedisUtil.get(UpmsConstant.SHIRO_SESSION_ID + "_" + id);
            // 过滤redis过期session
            if (null == session) {
                RedisUtil.lrem(UpmsConstant.SERVER_SESSION_IDS, 1, id);
                total = total - 1;
                continue;
            }
            UpmsSession upmsSession = (UpmsSession) SerializableUtil.deserialize(session);
            // 过滤未登录用户
            if (StringUtils.isNotBlank(upmsSession.getUsername())) {
                if(StringUtils.isBlank(username) || upmsSession.getUsername().contains(username)) {
                    rows.add(upmsSession);
                }
            }
        }
        jedis.close();
        int start = offset>rows.size()?rows.size():offset;
        int end = (offset+limit)>rows.size()?rows.size():(offset+limit);
        return new BasePageResult<Session>(rows.size(), rows.subList(start, end));
    }

    /**
     * 强制退出
     * 
     * @param ids
     * @return
     */
    public int forceout(String ids) {
        String[] sessionIds = ids.split(",");
        for (String sessionId : sessionIds) {
            // 会话增加强制退出属性标识，当此会话访问系统时，判断有该标识，则退出登录
            String session = RedisUtil.get(UpmsConstant.SHIRO_SESSION_ID + "_" + sessionId);
            UpmsSession upmsSession = (UpmsSession) SerializableUtil.deserialize(session);
            upmsSession.setStatus(UpmsSession.OnlineStatus.force_logout);
            upmsSession.setAttribute(UpmsConstant.FORCE_LOGOUT, true);
            RedisUtil.set(UpmsConstant.SHIRO_SESSION_ID + "_" + sessionId, SerializableUtil.serialize(upmsSession), (int) upmsSession.getTimeout() / 1000);
        }
        return sessionIds.length;
    }

    /**
     * 更改在线状态
     *
     * @param sessionId
     * @param onlineStatus
     */
    public void updateStatus(Serializable sessionId, Subject subject, UpmsSession.OnlineStatus onlineStatus) {
        UpmsSession session = (UpmsSession) doReadSession(sessionId);
        if (null == session) {
            return;
        }
        session.setStatus(onlineStatus);
        String realname = subject.getPrincipals().getRealmNames().size()>0?subject.getPrincipals().getRealmNames().iterator().next():"";
        String username = (String) subject.getPrincipal();
        session.setUsername(username+"["+realname+"]");
        RedisUtil.set(UpmsConstant.SHIRO_SESSION_ID + "_" + session.getId(), SerializableUtil.serialize(session), (int) session.getTimeout() / 1000);
    }

}
