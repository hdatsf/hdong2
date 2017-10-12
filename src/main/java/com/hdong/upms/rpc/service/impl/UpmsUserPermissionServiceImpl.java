package com.hdong.upms.rpc.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.hdong.common.util.SequenceUtil;
import com.hdong.upms.dao.enums.PermissionType;
import com.hdong.upms.dao.enums.SystemStatus;
import com.hdong.upms.dao.enums.UserPermissionType;
import com.hdong.upms.dao.mapper.UpmsPermissionMapper;
import com.hdong.upms.dao.mapper.UpmsSystemMapper;
import com.hdong.upms.dao.mapper.UpmsUserPermissionMapper;
import com.hdong.upms.dao.model.UpmsPermission;
import com.hdong.upms.dao.model.UpmsPermissionExample;
import com.hdong.upms.dao.model.UpmsSystem;
import com.hdong.upms.dao.model.UpmsSystemExample;
import com.hdong.upms.dao.model.UpmsUserPermission;
import com.hdong.upms.dao.model.UpmsUserPermissionExample;
import com.hdong.upms.rpc.api.UpmsUserPermissionService;

/**
 * UpmsUserPermissionService实现 Created by hdong on 2017/3/20.
 */
@Service
@BaseService
public class UpmsUserPermissionServiceImpl extends BaseServiceImpl<UpmsUserPermissionMapper, UpmsUserPermission, UpmsUserPermissionExample>
        implements UpmsUserPermissionService {

    private static Logger _log = LoggerFactory.getLogger(UpmsUserPermissionServiceImpl.class);

    @Autowired
    UpmsUserPermissionMapper upmsUserPermissionMapper;
    
    @Autowired
    private UpmsSystemMapper upmsSystemMapper;
    
    @Autowired
    private UpmsPermissionMapper upmsPermissionMapper;

    @Override
    public Logger getLogger() {
        return _log;
    }
    

    @Override
    public JSONArray getUserPermissionTreeByUserId(Integer usereId, UserPermissionType type) {
        // 用户已有权限
        UpmsUserPermissionExample upmsUserPermissionExample = new UpmsUserPermissionExample();
        upmsUserPermissionExample.createCriteria().andUserIdEqualTo(usereId).andTypeEqualTo(type);
        List<UpmsUserPermission> upmsUserPermissions = upmsUserPermissionMapper.selectByExample(upmsUserPermissionExample);
        Set<Integer> hasPermissions = new HashSet<Integer>();
        for(UpmsUserPermission item : upmsUserPermissions) {
            hasPermissions.add(item.getPermissionId());
        }
        // 用户权限树
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

    @Override
    @DataSource(name = DataSourceEnum.MASTER)
    @Transactional
    public int userPermissionSave(JSONArray datas, int id, UserPermissionType type) {
        List<Integer> deleteIds = new ArrayList<Integer>();
        for (int i = 0; i < datas.size(); i++) {
            JSONObject json = datas.getJSONObject(i);
            if (!json.getBoolean("checked")) {
                deleteIds.add(json.getIntValue("id"));
            } else {
                // 新增权限
                UpmsUserPermission upmsUserPermission = new UpmsUserPermission();
                upmsUserPermission.setUserId(id);
                upmsUserPermission.setPermissionId(json.getIntValue("id"));
                upmsUserPermission.setType(type);
                upmsUserPermission.setUserPermissionId(SequenceUtil.getInt(UpmsUserPermission.class));
                upmsUserPermissionMapper.insertSelective(upmsUserPermission);
            }
        }
        // 删除权限
        if (deleteIds.size() > 0) {
            UpmsUserPermissionExample upmsUserPermissionExample = new UpmsUserPermissionExample();
            upmsUserPermissionExample.createCriteria().andPermissionIdIn(deleteIds).andUserIdEqualTo(id);
            upmsUserPermissionMapper.deleteByExample(upmsUserPermissionExample);
        }
        return datas.size();
    }

}
