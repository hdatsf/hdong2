package com.hdong.upms.rpc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdong.common.annotation.BaseService;
import com.hdong.common.base.BaseServiceImpl;
import com.hdong.upms.dao.mapper.SysDictMapper;
import com.hdong.upms.dao.model.SysDict;
import com.hdong.upms.dao.model.SysDictExample;
import com.hdong.upms.rpc.api.SysDictService;

/**
* UpmsLogService实现
* Created by hdong on 2017/3/20.
*/
@Service
@Transactional
@BaseService
public class SysDictServiceImpl extends BaseServiceImpl<SysDictMapper, SysDict, SysDictExample> implements SysDictService {

    private static Logger _log = LoggerFactory.getLogger(SysDictServiceImpl.class);

    @Autowired
    SysDictMapper sysDictMapper;

    @Override
    public Logger getLogger() {
        return _log;
    }

}