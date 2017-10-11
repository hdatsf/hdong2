package com.hdong.upms.rpc.api;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.hdong.common.base.BaseService;
import com.hdong.upms.dao.model.UpmsPermission;
import com.hdong.upms.dao.model.UpmsPermissionExample;

/**
* UpmsPermissionService接口
* Created by hdong on 2017/3/20.
*/
public interface UpmsPermissionService extends BaseService<UpmsPermission, UpmsPermissionExample> {

    JSONArray getPermissionTree();
    
    int deleteByPermissionIds(List<Integer> ids);

}