package com.hdong.upms.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hdong.common.base.BaseController;
import com.hdong.common.util.PropertiesFileUtil;
import com.hdong.upms.dao.enums.SystemStatus;
import com.hdong.upms.dao.model.UpmsPermission;
import com.hdong.upms.dao.model.UpmsSystem;
import com.hdong.upms.dao.model.UpmsSystemExample;
import com.hdong.upms.dao.model.UpmsUser;
import com.hdong.upms.dao.model.UpmsUserExample;
import com.hdong.upms.rpc.api.UpmsApiService;
import com.hdong.upms.rpc.api.UpmsSystemService;
import com.hdong.upms.rpc.api.UpmsUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 后台controller Created by hdong on 2017/01/19.
 */
@Controller
@RequestMapping("/manage")
@Api(value = "后台管理", description = "后台管理")
public class ManageController extends BaseController {

    private static Logger _log = LoggerFactory.getLogger(ManageController.class);

    @Autowired
    private UpmsSystemService upmsSystemService;

    @Autowired
    private UpmsUserService upmsUserService;
    
    @Autowired
    private UpmsApiService upmsApiService;
    
    private static final String SYSTEM_NAME = PropertiesFileUtil.getInstance().get("system.name");

    @ApiOperation(value = "后台首页")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        // 已注册系统
        UpmsSystemExample upmsSystemExample = new UpmsSystemExample();
        upmsSystemExample.createCriteria().andStatusEqualTo(SystemStatus.NORMAL);
        List<UpmsSystem> upmsSystems = upmsSystemService.selectByExample(upmsSystemExample);
        modelMap.put("upmsSystems", upmsSystems);
        // 当前登录用户权限
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        UpmsUserExample userExample = new UpmsUserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        UpmsUser upmsUser = upmsUserService.selectFirstByExample(userExample);
        modelMap.put("upmsUser", upmsUser);
        UpmsSystem system = null;
        //获取当前系统编号
        for(UpmsSystem upmsSystem: upmsSystems) {
            if(upmsSystem.getName().equals(SYSTEM_NAME)) {
                system = upmsSystem;
                break;
            }
        }
        if(system == null) {
            _log.error("Has not system with name:{1}", SYSTEM_NAME);
            modelMap.put("upmsPermissions", new ArrayList<UpmsPermission>());
        }else {
            List<UpmsPermission> upmsPermissions = upmsApiService.selectMenuByUpmsUserIdAndSystemId(system.getSystemId(), upmsUser.getUserId());
            modelMap.put("upmsPermissions", upmsPermissions);
        }
        return "/manage/index.jsp";
    }

}
