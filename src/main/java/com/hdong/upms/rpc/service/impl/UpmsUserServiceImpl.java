package com.hdong.upms.rpc.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdong.common.annotation.BaseService;
import com.hdong.common.base.BaseServiceImpl;
import com.hdong.upms.dao.mapper.UpmsUserMapper;
import com.hdong.upms.dao.model.UpmsUser;
import com.hdong.upms.dao.model.UpmsUserExample;
import com.hdong.upms.rpc.api.UpmsUserService;

/**
 * UpmsUserService实现 Created by hdong on 2017/8/15.
 */
@Service
@Transactional
@BaseService
public class UpmsUserServiceImpl extends BaseServiceImpl<UpmsUserMapper, UpmsUser, UpmsUserExample>
    implements UpmsUserService {

  private static Logger _log = LoggerFactory.getLogger(UpmsUserServiceImpl.class);

  @Autowired
  UpmsUserMapper upmsUserMapper;

  @Override
  public Logger getLogger() {
      return _log;
  }
  @Override
  public UpmsUser createUser(UpmsUser upmsUser) {
    UpmsUserExample upmsUserExample = new UpmsUserExample();
    upmsUserExample.createCriteria().andUsernameEqualTo(upmsUser.getUsername());
    long count = upmsUserMapper.countByExample(upmsUserExample);
    if (count > 0) {
      _log.info("id ={1} is exist!",upmsUser.getUserId());
      return null;
    }
    upmsUserMapper.insert(upmsUser);
    return upmsUser;
  }
}
