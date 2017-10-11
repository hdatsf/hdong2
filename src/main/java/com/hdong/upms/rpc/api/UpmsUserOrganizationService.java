package com.hdong.upms.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.hdong.common.base.BaseService;
import com.hdong.upms.dao.model.UpmsUserOrganization;
import com.hdong.upms.dao.model.UpmsUserOrganizationExample;

/**
* UpmsUserOrganizationService接口
* Created by hdong on 2017/3/20.
*/
public interface UpmsUserOrganizationService extends BaseService<UpmsUserOrganization, UpmsUserOrganizationExample> {

    int userOrgizationSave(JSONArray datas, int id);
}