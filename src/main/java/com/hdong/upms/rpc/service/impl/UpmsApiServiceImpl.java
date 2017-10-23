package com.hdong.upms.rpc.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdong.common.db.DataSource;
import com.hdong.common.db.DataSourceEnum;
import com.hdong.upms.dao.enums.UserLocked;
import com.hdong.upms.dao.mapper.UpmsApiMapper;
import com.hdong.upms.dao.mapper.UpmsRolePermissionMapper;
import com.hdong.upms.dao.mapper.UpmsSystemMapper;
import com.hdong.upms.dao.mapper.UpmsUserMapper;
import com.hdong.upms.dao.model.UpmsPermission;
import com.hdong.upms.dao.model.UpmsRole;
import com.hdong.upms.dao.model.UpmsSystem;
import com.hdong.upms.dao.model.UpmsUser;
import com.hdong.upms.dao.model.UpmsUserExample;
import com.hdong.upms.rpc.api.UpmsApiService;

/**
 * UpmsApiService实现 
 * Created by hdong on 2016/01/19.
 */
@Service
public class UpmsApiServiceImpl implements UpmsApiService {

    private static Logger _log = LoggerFactory.getLogger(UpmsApiServiceImpl.class);

    @Autowired
    UpmsUserMapper upmsUserMapper;

    @Autowired
    UpmsApiMapper upmsApiMapper;

    @Autowired
    UpmsRolePermissionMapper upmsRolePermissionMapper;

    @Autowired
    UpmsSystemMapper upmsSystemMapper;

    /**
     * 根据username获取用户角色和权限集合
     * 
     * @param username
     * @param systemName
     * @return
     */
    @Override
    @DataSource(name = DataSourceEnum.SLAVE)
    @Transactional
    public List<Set<String>> selectRolesPermissionsByName(String username) {
        UpmsUserExample userExample = new UpmsUserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<UpmsUser> userList = upmsUserMapper.selectByExample(userExample);
        if (userList.size() == 0) {
            _log.error("Has not user with name:{1}", username);
            return null;
        }
        UpmsUser upmsUser = userList.get(0);
        if (UserLocked.LOCKED == upmsUser.getLocked()) {
            _log.info("user is locked : username={}", username);
            return null;
        }
        // 当前用户所有角色
        List<UpmsRole> upmsRoles = selectUpmsRoleByUpmsUserId(upmsUser.getUserId());
        Set<String> roles = new HashSet<String>();
        for (UpmsRole upmsRole : upmsRoles) {
            if (StringUtils.isNotBlank(upmsRole.getName())) {
                roles.add(upmsRole.getName());
            }
        }
        Set<String> permissions = new HashSet<String>();
        // 当前用户所有权限
        List<UpmsPermission> upmsPermissions = upmsApiMapper.selectUpmsPermissionByUpmsUserId(upmsUser.getUserId());
        for (UpmsPermission upmsPermission : upmsPermissions) {
            if (StringUtils.isNotBlank(upmsPermission.getPermissionValue())) {
                permissions.add(upmsPermission.getPermissionValue());
            }
        }
        List<Set<String>> retList = new ArrayList<Set<String>>();
        retList.add(roles);
        retList.add(permissions);
        return retList;
    }

    /**
     * 根据username、systemName获取用户角色和权限集合，并缓存
     * 
     * @param username
     * @param systemName
     * @return
     */
    @Override
    @Cacheable(value = "market-ehcache", key = "'selectRolesPermissionsByName:username_'+ #username")
    public List<Set<String>> selectRolesPermissionsByNameByCache(String username) {
        return selectRolesPermissionsByName(username);
    }

    /**
     * 根据用户id获取菜单
     * 
     * @param upmsUserId
     * @return
     */
    @Override
    @Transactional
    @DataSource(name = DataSourceEnum.SLAVE)
    public List<UpmsPermission> selectMenuByUpmsUserName(String username) {
        UpmsUserExample userExample = new UpmsUserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<UpmsUser> userList = upmsUserMapper.selectByExample(userExample);
        if (userList.size() == 0) {
            _log.error("Has not user with name:{1}", username);
            return null;
        }
        UpmsUser upmsUser = userList.get(0);
        if (UserLocked.LOCKED == upmsUser.getLocked()) {
            _log.info("user is locked : username={}", username);
            return null;
        }
        return upmsApiMapper.selectMenuByUpmsUserId(upmsUser.getUserId());
    }

    /**
     * 根据用户id获取所属的角色
     * 
     * @param upmsUserId
     * @return
     */
    @Transactional
    @DataSource(name = DataSourceEnum.SLAVE)
    private List<UpmsRole> selectUpmsRoleByUpmsUserId(Integer upmsUserId) {
        // 用户不存在或锁定状态
        UpmsUser upmsUser = upmsUserMapper.selectByPrimaryKey(upmsUserId);
        if (null == upmsUser || UserLocked.LOCKED == upmsUser.getLocked()) {
            _log.info("selectUpmsRoleByUpmsUserId : upmsUserId={}", upmsUserId);
            return null;
        }
        List<UpmsRole> upmsRoles = upmsApiMapper.selectUpmsRoleByUpmsUserId(upmsUserId);
        return upmsRoles;
    }
    
    @Transactional
    @DataSource(name = DataSourceEnum.SLAVE)
    public UpmsUser selectUserByUsername(String username) {
        UpmsUserExample ex = new UpmsUserExample();
        ex.createCriteria().andUsernameEqualTo(username);
        List<UpmsUser> list = upmsUserMapper.selectByExample(ex);
        return list.size() > 0 ? list.get(0) : null;
    }
    
    @Override
    @Transactional
    @DataSource(name = DataSourceEnum.SLAVE)
    public List<UpmsSystem> selectSystemsByUserId(Integer upmsUserId) {
        return upmsApiMapper.selectSystemsByUserId(upmsUserId);
    }
}
