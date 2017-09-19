package com.hdong.upms.rpc.api;

import com.hdong.common.base.BaseService;
import com.hdong.upms.dao.model.UpmsUser;
import com.hdong.upms.dao.model.UpmsUserExample;

/**
* UpmsUserService接口
* Created by hdong on 2017/8/15.
*/
public interface UpmsUserService extends BaseService<UpmsUser, UpmsUserExample> {
  public UpmsUser createUser(UpmsUser upmsUser);
}