package com.hdong.upms.rpc.api;

import com.hdong.common.base.BaseService;
import com.hdong.upms.dao.model.UpmsUserOrganization;
import com.hdong.upms.dao.model.UpmsUserOrganizationExample;

/**
* UpmsUserOrganizationService接口
* Created by hdong on 2017/3/20.
*/
public interface UpmsUserOrganizationService extends BaseService<UpmsUserOrganization, UpmsUserOrganizationExample> {

    /**
     * 用户组织
     * @param organizationIds 组织ids
     * @param id 用户id
     * @return
     */
    int organization(String[] organizationIds, int id);

}