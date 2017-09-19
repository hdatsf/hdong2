package com.hdong.upms.server.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hdong.upms.dao.model.UpmsUser;
import com.hdong.upms.dao.model.UpmsUserExample;
import com.hdong.upms.rpc.api.UpmsApiService;
import com.hdong.upms.rpc.api.UpmsUserService;

/**
 * 登录信息拦截器
 * Created by hdong on 2017/2/11.
 */
public class UpmsInterceptor extends HandlerInterceptorAdapter {

    //private static Logger _log = LoggerFactory.getLogger(UpmsInterceptor.class);

    @Autowired
    UpmsApiService upmsApiService;
    
    @Autowired
    UpmsUserService upmsUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 过滤ajax
        if (null != request.getHeader("X-Requested-With") && request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        }
        // 登录信息
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        UpmsUserExample userExample = new UpmsUserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        UpmsUser upmsUser = upmsUserService.selectFirstByExample(userExample);
        request.setAttribute("upmsUser", upmsUser);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }

}
