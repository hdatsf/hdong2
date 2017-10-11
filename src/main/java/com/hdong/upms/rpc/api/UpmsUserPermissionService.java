package com.hdong.upms.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.hdong.common.base.BaseService;
import com.hdong.upms.dao.enums.UserPermissionType;
import com.hdong.upms.dao.model.UpmsUserPermission;
import com.hdong.upms.dao.model.UpmsUserPermissionExample;

/**
* UpmsUserPermissionService接口
* Created by hdong on 2017/3/20.
*/
public interface UpmsUserPermissionService extends BaseService<UpmsUserPermission, UpmsUserPermissionExample> {
    
    JSONArray getUserPermissionTreeByUserId(Integer usereId, UserPermissionType type);
    
    int userPermissionSave(JSONArray datas, int id, UserPermissionType type);

}