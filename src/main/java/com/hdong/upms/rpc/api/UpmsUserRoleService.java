package com.hdong.upms.rpc.api;

import com.hdong.common.base.BaseService;
import com.hdong.upms.dao.model.UpmsUserRole;
import com.hdong.upms.dao.model.UpmsUserRoleExample;

/**
* UpmsUserRoleService接口
* Created by hdong on 2017/3/20.
*/
public interface UpmsUserRoleService extends BaseService<UpmsUserRole, UpmsUserRoleExample> {

    /**
     * 用户角色
     * @param roleIds 角色ids
     * @param id 用户id
     * @return
     */
    int role(String[] roleIds, int id);

}