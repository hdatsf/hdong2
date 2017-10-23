package com.hdong.upms.server.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hdong.common.base.BaseController;
import com.hdong.upms.dao.model.UpmsPermission;
import com.hdong.upms.dao.model.UpmsSystem;
import com.hdong.upms.dao.model.UpmsSystemExample;
import com.hdong.upms.dao.model.UpmsUser;
import com.hdong.upms.rpc.api.UpmsApiService;
import com.hdong.upms.rpc.api.UpmsSystemService;

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

    @Autowired
    private UpmsSystemService upmsSystemService;

    @ApiOperation(value = "后台首页")
    @RequestMapping(value = "/index/{systemname}", method = RequestMethod.GET)
    public String index(@PathVariable("systemname") String systemname, ModelMap modelMap) {
        UpmsSystemExample ex = new UpmsSystemExample();
        ex.createCriteria().andNameEqualTo(systemname);
        UpmsSystem system = upmsSystemService.selectFirstByExample(ex);
        if(system==null) {
            modelMap.put("useCookieFlag", true);
        }else {
            modelMap.put("useCookieFlag", false);
            modelMap.put("systemname", systemname);
        }
        return indexMain(modelMap);
    }

    @ApiOperation(value = "后台首页")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        modelMap.put("useCookieFlag", true);
        return indexMain(modelMap);
    }

    private String indexMain(ModelMap modelMap) {
        // 当前用户
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        UpmsUser upmsUser = upmsApiService.selectUserByUsername(username);
        modelMap.put("upmsUser", upmsUser);
        // 用户可用系统集合
        List<UpmsSystem> upmsSystems = upmsApiService.selectSystemsByUserId(upmsUser.getUserId());
        modelMap.put("upmsSystems", upmsSystems);
        List<UpmsPermission> upmsPermissions = upmsApiService.selectMenuByUpmsUserName(username);
        modelMap.put("upmsPermissions", upmsPermissions);
        return "/manage/index.jsp";
    }

    @ApiOperation(value = "首页获取系统信息")
    @RequestMapping(value = "/systeminfo/{systemId}", method = RequestMethod.GET)
    @ResponseBody
    public String systeminfo(@PathVariable("systemId") Integer systemId) {
        return upmsSystemService.selectByPrimaryKey(systemId).getDescription();
    }

}
