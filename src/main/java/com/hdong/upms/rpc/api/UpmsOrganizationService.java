package com.hdong.upms.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.hdong.common.base.BaseService;
import com.hdong.upms.dao.model.UpmsOrganization;
import com.hdong.upms.dao.model.UpmsOrganizationExample;

/**
* UpmsOrganizationService接口
* Created by hdong on 2017/3/20.
*/
public interface UpmsOrganizationService extends BaseService<UpmsOrganization, UpmsOrganizationExample> {
    
    JSONArray getOrgTree();
    
    JSONArray getUserOrgTreeByUserId(Integer userId);
}