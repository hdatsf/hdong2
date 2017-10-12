package com.hdong.upms.rpc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdong.common.annotation.BaseService;
import com.hdong.common.base.BaseServiceImpl;
import com.hdong.upms.dao.mapper.UpmsRoleMapper;
import com.hdong.upms.dao.model.UpmsRole;
import com.hdong.upms.dao.model.UpmsRoleExample;
import com.hdong.upms.rpc.api.UpmsRoleService;

/**
* UpmsRoleService实现
* Created by hdong on 2017/3/20.
*/
@Service
@BaseService
public class UpmsRoleServiceImpl extends BaseServiceImpl<UpmsRoleMapper, UpmsRole, UpmsRoleExample> implements UpmsRoleService {

    private static Logger _log = LoggerFactory.getLogger(UpmsRoleServiceImpl.class);

    @Autowired
    UpmsRoleMapper upmsRoleMapper;

    @Override
    public Logger getLogger() {
        return _log;
    }
}