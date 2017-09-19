package com.hdong.upms.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.hdong.common.base.BaseService;
import com.hdong.upms.dao.model.UpmsUserPermission;
import com.hdong.upms.dao.model.UpmsUserPermissionExample;

/**
* UpmsUserPermissionService接口
* Created by hdong on 2017/3/20.
*/
public interface UpmsUserPermissionService extends BaseService<UpmsUserPermission, UpmsUserPermissionExample> {

    /**
     * 用户权限
     * @param datas 权限数据
     * @param id 用户id
     * @return
     */
    int permission(JSONArray datas, int id);

}