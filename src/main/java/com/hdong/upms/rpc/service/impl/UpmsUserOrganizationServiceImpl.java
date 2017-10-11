package com.hdong.upms.rpc.service.impl;

import java.util.ArrayList;
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
import com.hdong.common.util.SequenceUtil;
import com.hdong.upms.dao.mapper.UpmsUserOrganizationMapper;
import com.hdong.upms.dao.mapper.UpmsUserPermissionMapper;
import com.hdong.upms.dao.model.UpmsUserOrganization;
import com.hdong.upms.dao.model.UpmsUserOrganizationExample;
import com.hdong.upms.rpc.api.UpmsUserOrganizationService;

/**
* UpmsUserOrganizationService实现
* Created by hdong on 2017/3/20.
*/
@Service
@Transactional
@BaseService
public class UpmsUserOrganizationServiceImpl extends BaseServiceImpl<UpmsUserOrganizationMapper, UpmsUserOrganization, UpmsUserOrganizationExample> implements UpmsUserOrganizationService {

    private static Logger _log = LoggerFactory.getLogger(UpmsUserOrganizationServiceImpl.class);

    @Autowired
    UpmsUserOrganizationMapper upmsUserOrganizationMapper;
    
    @Autowired
    UpmsUserPermissionMapper upmsUserPermissionMapper;

    @Override
    public Logger getLogger() {
        return _log;
    }
    @Override
    public int userOrgizationSave(JSONArray datas, int id) {
        List<Integer> deleteIds = new ArrayList<>();
        for (int i = 0; i < datas.size(); i ++) {
            JSONObject json = datas.getJSONObject(i);
            if (!json.getBoolean("checked")) {
                deleteIds.add(json.getIntValue("id"));
            } else {
                // 新增权限
                UpmsUserOrganization upmsUserOrganization = new UpmsUserOrganization();
                upmsUserOrganization.setUserOrganizationId(SequenceUtil.getInt(UpmsUserOrganization.class));
                upmsUserOrganization.setUserId(id);
                upmsUserOrganization.setOrganizationId(json.getIntValue("id"));
                upmsUserOrganizationMapper.insertSelective(upmsUserOrganization);
            }
        }
        // 删除权限
        if (deleteIds.size() > 0) {
            UpmsUserOrganizationExample upmsUserOrganizationExample = new UpmsUserOrganizationExample();
            upmsUserOrganizationExample.createCriteria().andOrganizationIdIn(deleteIds).andUserIdEqualTo(id);
            upmsUserOrganizationMapper.deleteByExample(upmsUserOrganizationExample);
        }
        return datas.size();
    }
}