package com.hdong.upms.rpc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hdong.common.annotation.BaseService;
import com.hdong.common.base.BaseServiceImpl;
import com.hdong.common.db.DataSource;
import com.hdong.common.db.DataSourceEnum;
import com.hdong.common.util.SequenceUtil;
import com.hdong.upms.dao.enums.PermissionType;
import com.hdong.upms.dao.enums.SystemStatus;
import com.hdong.upms.dao.mapper.UpmsPermissionMapper;
import com.hdong.upms.dao.mapper.UpmsRolePermissionMapper;
import com.hdong.upms.dao.mapper.UpmsSystemMapper;
import com.hdong.upms.dao.model.UpmsPermission;
import com.hdong.upms.dao.model.UpmsPermissionExample;
import com.hdong.upms.dao.model.UpmsRolePermission;
import com.hdong.upms.dao.model.UpmsRolePermissionExample;
import com.hdong.upms.dao.model.UpmsSystem;
import com.hdong.upms.dao.model.UpmsSystemExample;
import com.hdong.upms.rpc.api.UpmsRolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
* UpmsRolePermissionService实现
* Created by hdong on 2017/3/20.
*/
@Service
@BaseService
public class UpmsRolePermissionServiceImpl extends BaseServiceImpl<UpmsRolePermissionMapper, UpmsRolePermission, UpmsRolePermissionExample> implements UpmsRolePermissionService {

    private static Logger _log = LoggerFactory.getLogger(UpmsRolePermissionServiceImpl.class);

    @Autowired
    private UpmsRolePermissionMapper upmsRolePermissionMapper;

    @Autowired
    private UpmsSystemMapper upmsSystemMapper;
    
    @Autowired
    private UpmsPermissionMapper upmsPermissionMapper;
    
    @Override
    public Logger getLogger() {
        return _log;
    }
    
    @Override
    @DataSource(name = DataSourceEnum.MASTER)
    @Transactional
    public int rolePermissionSave(JSONArray datas, int id) {
        List<Integer> deleteIds = new ArrayList<Integer>();
        for (int i = 0; i < datas.size(); i ++) {
            JSONObject json = datas.getJSONObject(i);
            if (!json.getBoolean("checked")) {
                deleteIds.add(json.getIntValue("id"));
            } else {
                // 新增权限
                UpmsRolePermission upmsRolePermission = new UpmsRolePermission();
                upmsRolePermission.setRoleId(id);
                upmsRolePermission.setPermissionId(json.getIntValue("id"));
                upmsRolePermission.setRolePermissionId(SequenceUtil.getInt(UpmsRolePermission.class));
                upmsRolePermissionMapper.insertSelective(upmsRolePermission);
            }
        }
        // 删除权限
        if (deleteIds.size() > 0) {
            UpmsRolePermissionExample upmsRolePermissionExample = new UpmsRolePermissionExample();
            upmsRolePermissionExample.createCriteria().andPermissionIdIn(deleteIds).andRoleIdEqualTo(id);
            upmsRolePermissionMapper.deleteByExample(upmsRolePermissionExample);
        }
        return datas.size();
    }
    
    @Override
    public JSONArray getRolePermissionTreeByRoleId(Integer roleId) {
        // 角色已有权限
        UpmsRolePermissionExample upmsRolePermissionExample = new UpmsRolePermissionExample();
        upmsRolePermissionExample.createCriteria().andRoleIdEqualTo(roleId);
        List<UpmsRolePermission> rolePermissions = upmsRolePermissionMapper.selectByExample(upmsRolePermissionExample);
        Set<Integer> hasPermissions = new HashSet<Integer>();
        for(UpmsRolePermission item : rolePermissions) {
            hasPermissions.add(item.getPermissionId());
        }
        //角色权限树
        JSONArray nodes = new JSONArray();
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
            node.put("nocheck", true);
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
                if(hasPermissions.contains(upmsPermission.getPermissionId())) {
                    node.put("checked", true);
                }
                nodes.add(node);
            }
        }
        return nodes;
    }

}