package com.hdong.upms.rpc.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hdong.common.annotation.BaseService;
import com.hdong.common.base.BaseServiceImpl;
import com.hdong.common.db.DataSource;
import com.hdong.common.db.DataSourceEnum;
import com.hdong.common.db.DynamicDataSource;
import com.hdong.upms.dao.enums.PermissionType;
import com.hdong.upms.dao.enums.SystemStatus;
import com.hdong.upms.dao.mapper.UpmsPermissionMapper;
import com.hdong.upms.dao.mapper.UpmsRolePermissionMapper;
import com.hdong.upms.dao.mapper.UpmsSystemMapper;
import com.hdong.upms.dao.mapper.UpmsUserPermissionMapper;
import com.hdong.upms.dao.model.UpmsPermission;
import com.hdong.upms.dao.model.UpmsPermissionExample;
import com.hdong.upms.dao.model.UpmsRolePermissionExample;
import com.hdong.upms.dao.model.UpmsSystem;
import com.hdong.upms.dao.model.UpmsSystemExample;
import com.hdong.upms.dao.model.UpmsUserPermissionExample;
import com.hdong.upms.rpc.api.UpmsPermissionService;

/**
* UpmsPermissionService实现
* Created by hdong on 2017/3/20.
*/
@Service
@BaseService
public class UpmsPermissionServiceImpl extends BaseServiceImpl<UpmsPermissionMapper, UpmsPermission, UpmsPermissionExample> implements UpmsPermissionService {

    private static Logger _log = LoggerFactory.getLogger(UpmsPermissionServiceImpl.class);

    @Autowired
    private UpmsSystemMapper upmsSystemMapper;

    @Autowired
    private UpmsPermissionMapper upmsPermissionMapper;

    @Autowired
    private UpmsUserPermissionMapper upmsUserPermissionMapper;
    
    @Autowired
    private UpmsRolePermissionMapper upmsRolePermissionMapper;

    @Override
    public Logger getLogger() {
        return _log;
    }
    
    /**
     * 权限树
     */
    @Override
    public JSONArray getPermissionTree() {
        JSONArray nodes = new JSONArray();
        // 系统
        UpmsSystemExample upmsSystemExample = new UpmsSystemExample();
        upmsSystemExample.createCriteria().andStatusEqualTo(SystemStatus.NORMAL);
        upmsSystemExample.setOrderByClause("orders asc");
        List<UpmsSystem> upmsSystems = upmsSystemMapper.selectByExample(upmsSystemExample);
        for (UpmsSystem upmsSystem : upmsSystems) {
            JSONObject node = new JSONObject();
            node.put("id", "system_"+upmsSystem.getSystemId());
            node.put("pId", -1);
            node.put("type", PermissionType.SYSTEM);
            node.put("systemId", upmsSystem.getSystemId());
            node.put("name", upmsSystem.getTitle());
            node.put("open", true);
            nodes.add(node);
            UpmsPermissionExample upmsPermissionExample = new UpmsPermissionExample();
            upmsPermissionExample.createCriteria().andSystemIdEqualTo(upmsSystem.getSystemId());
            upmsPermissionExample.setOrderByClause("orders asc");
            List<UpmsPermission> upmsPermissions = upmsPermissionMapper.selectByExample(upmsPermissionExample);
            for (UpmsPermission upmsPermission: upmsPermissions) {
                node = new JSONObject();
                node.put("id", upmsPermission.getPermissionId());
                node.put("type", upmsPermission.getType());
                node.put("systemId", upmsPermission.getSystemId());
                node.put("name", upmsPermission.getName());
                node.put("open", true);
                if (upmsPermission.getPid().intValue() == 0) {
                    node.put("pId", "system_"+upmsSystem.getSystemId());
                }else {
                    node.put("pId", upmsPermission.getPid());
                }
                nodes.add(node);
            }
        }
        return nodes;
    }

    @Override
    @DataSource(name = DataSourceEnum.MASTER)
    @Transactional
    public int deleteByPermissionIds(List<Integer> ids) {
        try {
            if (ids.isEmpty()) {
                return 0;
            }
            //删权限数据
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER);
            UpmsPermissionExample ex = new UpmsPermissionExample();
            ex.createCriteria().andPermissionIdIn(ids);
            int count = upmsPermissionMapper.deleteByExample(ex);
            //删用户权限
            UpmsUserPermissionExample ex2 = new UpmsUserPermissionExample();
            ex2.createCriteria().andPermissionIdIn(ids);
            upmsUserPermissionMapper.deleteByExample(ex2);
            //删角色权限
            UpmsRolePermissionExample ex3 = new UpmsRolePermissionExample();
            ex3.createCriteria().andPermissionIdIn(ids);
            upmsRolePermissionMapper.deleteByExample(ex3);
            return count;
        } catch (Exception e) {
            getLogger().error("deleteByPrimaryKeys error:", e);
        } finally{
            DynamicDataSource.clearDataSource();
        }
        return 0;
    }

}