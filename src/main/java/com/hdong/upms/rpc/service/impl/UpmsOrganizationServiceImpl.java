package com.hdong.upms.rpc.service.impl;

import com.hdong.common.annotation.BaseService;
import com.hdong.common.base.BaseServiceImpl;
import com.hdong.upms.dao.mapper.UpmsOrganizationMapper;
import com.hdong.upms.dao.model.UpmsOrganization;
import com.hdong.upms.dao.model.UpmsOrganizationExample;
import com.hdong.upms.rpc.api.UpmsOrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}