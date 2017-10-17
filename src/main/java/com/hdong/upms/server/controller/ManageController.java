package com.hdong.upms.server.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hdong.common.base.BaseController;
import com.hdong.common.util.PropertiesFileUtil;
import com.hdong.upms.dao.model.UpmsPermission;
import com.hdong.upms.dao.model.UpmsUser;
import com.hdong.upms.rpc.api.UpmsApiService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 后台controller Created by hdong on 2017/01/19.
 */
@Controller
@RequestMapping("/manage")
@Api(value = "后台管理", description = "后台管理")
public class ManageController extends BaseController {

    // private static Logger _log = LoggerFactory.getLogger(ManageController.class);

    @Autowired
    private UpmsApiService upmsApiService;

    private static final String SYSTEM_NAME = PropertiesFileUtil.getInstance().get("system.name");

    @ApiOperation(value = "后台首页")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        // 当前登录用户权限
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        UpmsUser upmsUser = upmsApiService.selectUserByUsername(username);
        modelMap.put("upmsUser", upmsUser);
        List<UpmsPermission> upmsPermissions = upmsApiService.selectMenuByUpmsUserNameAndSystemName(username, SYSTEM_NAME);
        modelMap.put("upmsPermissions", upmsPermissions);
        return "/manage/index.jsp";
    }

}
