package com.hdong.upms.rpc.service.impl;

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
import com.hdong.upms.dao.mapper.UpmsOrganizationMapper;
import com.hdong.upms.dao.mapper.UpmsUserOrganizationMapper;
import com.hdong.upms.dao.model.UpmsOrganization;
import com.hdong.upms.dao.model.UpmsOrganizationExample;
import com.hdong.upms.dao.model.UpmsUserOrganization;
import com.hdong.upms.dao.model.UpmsUserOrganizationExample;
import com.hdong.upms.rpc.api.UpmsOrganizationService;

/**
* UpmsOrganizationService实现
* Created by hdong on 2017/3/20.
*/
@Service
@Transactional
@BaseService
public class UpmsOrganizationServiceImpl extends BaseServiceImpl<UpmsOrganizationMapper, UpmsOrganization, UpmsOrganizationExample> implements UpmsOrganizationService {

    private static Logger _log = LoggerFactory.getLogger(UpmsOrganizationServiceImpl.class);

    @Autowired
    UpmsOrganizationMapper upmsOrganizationMapper;
    
    @Autowired
    UpmsUserOrganizationMapper upmsUserOrganizationMapper;

    @Override
    public Logger getLogger() {
        return _log;
    }
    
    
    /**
     * 组织树
     */
    @Override
    public JSONArray getOrgTree() {
        JSONArray nodes = new JSONArray();
        UpmsOrganizationExample upmsOrganizationExample = new UpmsOrganizationExample();
        upmsOrganizationExample.setOrderByClause("organization_id asc");
        List<UpmsOrganization> orgList = upmsOrganizationMapper.selectByExample(upmsOrganizationExample);
        for(UpmsOrganization item : orgList) {
            JSONObject node = new JSONObject();
            node.put("id", item.getOrganizationId());
            node.put("pId", item.getPid());
            node.put("name", item.getName());
            node.put("open", true);
            nodes.add(node);
        }
        return nodes;
    }
    
    /**
     * 用户组织树
     */
    @Override
    public JSONArray getUserOrgTreeByUserId(Integer userId) {
        UpmsUserOrganizationExample ex = new UpmsUserOrganizationExample();
        ex.createCriteria().andUserIdEqualTo(userId);
        List<UpmsUserOrganization> userOrgs = upmsUserOrganizationMapper.selectByExample(ex);
        Set<Integer> hasOrgs = new HashSet<Integer>();
        for(UpmsUserOrganization item : userOrgs) {
            hasOrgs.add(item.getOrganizationId());
        }
        JSONArray nodes = new JSONArray();
        UpmsOrganizationExample upmsOrganizationExample = new UpmsOrganizationExample();
        upmsOrganizationExample.setOrderByClause("organization_id asc");
        List<UpmsOrganization> orgs = upmsOrganizationMapper.selectByExample(upmsOrganizationExample);
        JSONObject node;
        for (UpmsOrganization org: orgs) {
            node = new JSONObject();
            node.put("id", org.getOrganizationId());
            node.put("name", org.getName());
            node.put("open", true);
            node.put("pId", org.getPid());
            if(hasOrgs.contains(org.getOrganizationId())) {
                node.put("checked", true);
            }
            nodes.add(node);
        }
        return nodes;
    }
}