package com.hdong.upms.server.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hdong.common.base.BaseController;
import com.hdong.common.shiro.session.UpmsSession;
import com.hdong.common.shiro.session.UpmsSessionDao;
import com.hdong.common.util.RedisUtil;
import com.hdong.upms.common.constant.UpmsConstant;
import com.hdong.upms.common.constant.UpmsResult;
import com.hdong.upms.common.constant.UpmsResultConstant;
import com.hdong.upms.dao.model.UpmsSystem;
import com.hdong.upms.dao.model.UpmsSystemExample;
import com.hdong.upms.rpc.api.UpmsSystemService;
import com.hdong.upms.rpc.api.UpmsUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 单点登录管理 Created by hdong on 2016/12/10.
 */
@Controller
@RequestMapping("/sso")
@Api(value = "单点登录管理", description = "单点登录管理")
public class SSOController extends BaseController {

    // private final static Logger _log = LoggerFactory.getLogger(SSOController.class);
    @Autowired
    UpmsSystemService upmsSystemService;

    @Autowired
    UpmsUserService upmsUserService;

    @Autowired
    UpmsSessionDao upmsSessionDao;

    @ApiOperation(value = "登录1")
    @RequestMapping(value = "/login/{systemname}", method = RequestMethod.GET)
    public String login(@PathVariable("systemname") String systemname, HttpServletRequest request, ModelMap modelMap) throws Exception {
        return loginMain(request, modelMap, systemname);
    }

    @ApiOperation(value = "登录2")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, ModelMap modelMap) {
        String systemname = request.getParameter("systemname");
        return loginMain(request, modelMap, systemname);
    }

    private String loginMain(HttpServletRequest request, ModelMap modelMap, String systemname) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String serverSessionId = session.getId().toString();
        // 判断是否已登录，如果已登录，则回跳
        String code = RedisUtil.get(UpmsConstant.SERVER_SESSION_ID + "_" + serverSessionId);
        // code校验值
        if (StringUtils.isNotBlank(code)) {
            if (StringUtils.isBlank(systemname)) {
                return "redirect:/manage/index";
            } else {
                return "redirect:/manage/index/" + systemname;
            }
        }
        String forceLogout = request.getParameter("forceLogout");
        if (!StringUtils.isBlank(forceLogout)) {
            modelMap.put("forceLogout", forceLogout);
        }
        UpmsSystem system = null;
        if (StringUtils.isNotBlank(systemname)) {
            UpmsSystemExample ex = new UpmsSystemExample();
            ex.createCriteria().andNameEqualTo(systemname);
            system = upmsSystemService.selectFirstByExample(ex);
        }
        modelMap.put("systemname", system==null?"":system.getName());
        modelMap.put("title", system==null?"":system.getTitle());
        return "/sso/login.jsp";
    }

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        if (StringUtils.isBlank(username)) {
            return new UpmsResult(UpmsResultConstant.EMPTY_USERNAME);
        }
        if (StringUtils.isBlank(password)) {
            return new UpmsResult(UpmsResultConstant.EMPTY_PASSWORD);
        }
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        // 判断是否已登录，如果已登录，则回跳，防止重复登录
        String hasCode = RedisUtil.get(UpmsConstant.SERVER_SESSION_ID + "_" + sessionId);
        // code校验值
        if (StringUtils.isBlank(hasCode)) {
            // 使用shiro认证
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            try {
                if (BooleanUtils.toBoolean(rememberMe)) {
                    usernamePasswordToken.setRememberMe(true);
                } else {
                    usernamePasswordToken.setRememberMe(false);
                }
                subject.login(usernamePasswordToken);
            } catch (UnknownAccountException e) {
                return new UpmsResult(UpmsResultConstant.INVALID_USERNAME);
            } catch (IncorrectCredentialsException e) {
                return new UpmsResult(UpmsResultConstant.INVALID_PASSWORD);
            } catch (LockedAccountException e) {
                return new UpmsResult(UpmsResultConstant.INVALID_ACCOUNT);
            }
            // 更新session状态
            upmsSessionDao.updateStatus(sessionId, subject, UpmsSession.OnlineStatus.on_line);
            // 全局会话sessionId列表，供会话管理
            RedisUtil.lpush(UpmsConstant.SERVER_SESSION_IDS, sessionId.toString());
            // 默认验证帐号密码正确，创建code
            String code = UUID.randomUUID().toString();
            // 全局会话的code
            RedisUtil.set(UpmsConstant.SERVER_SESSION_ID + "_" + sessionId, code, (int) subject.getSession().getTimeout() / 1000);
            // code校验值
            RedisUtil.set(UpmsConstant.SERVER_CODE + "_" + code, code, (int) subject.getSession().getTimeout() / 1000);
        }
        // 回跳登录前地址
        String systemname = request.getParameter("systemname");
        return new UpmsResult(UpmsResultConstant.SUCCESS, "/manage/index"+(StringUtils.isNotBlank(systemname)?("/"+systemname):""));
    }

    @ApiOperation(value = "校验code")
    @RequestMapping(value = "/code", method = RequestMethod.POST)
    @ResponseBody
    public Object code(HttpServletRequest request) {
        String codeParam = request.getParameter("code");
        String code = RedisUtil.get(UpmsConstant.SERVER_CODE + "_" + codeParam);
        if (StringUtils.isBlank(codeParam) || !codeParam.equals(code)) {
            new UpmsResult(UpmsResultConstant.FAILED, "无效code");
        }
        return new UpmsResult(UpmsResultConstant.SUCCESS, code);
    }

    @ApiOperation(value = "退出登录")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(String systemname) {
        // shiro退出登录
        SecurityUtils.getSubject().logout();
        if(StringUtils.isNotBlank(systemname)) {
            return "redirect:/sso/login/"+systemname;
        }else {
            return "redirect:/sso/login";
        }
    }

}
