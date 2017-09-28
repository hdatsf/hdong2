package com.hdong.upms.rpc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdong.common.annotation.BaseService;
import com.hdong.common.base.BaseServiceImpl;
import com.hdong.upms.dao.mapper.UpmsSystemMapper;
import com.hdong.upms.dao.model.UpmsSystem;
import com.hdong.upms.dao.model.UpmsSystemExample;
import com.hdong.upms.rpc.api.UpmsSystemService;

/**
* UpmsSystemService实现
* Created by hdong on 2017/3/20.
*/
@Service
@BaseService
@Transactional
public class UpmsSystemServiceImpl extends BaseServiceImpl<UpmsSystemMapper, UpmsSystem, UpmsSystemExample> implements UpmsSystemService {

    private static Logger _log = LoggerFactory.getLogger(UpmsSystemServiceImpl.class);

    @Autowired
    UpmsSystemMapper upmsSystemMapper;

    @Override
    public Logger getLogger() {
        return _log;
    }
}