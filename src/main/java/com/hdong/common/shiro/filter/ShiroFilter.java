package com.hdong.common.shiro.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.hdong.common.shiro.session.UpmsSession;
import com.hdong.common.util.ServletUtil;
import com.hdong.common.web.util.RequestUtil;
/**
 * 如果是静态资源不需要经过shiro验证
 * 
 * @author hdong
 * @creation 2017年10月20日
 */
public class ShiroFilter extends DelegatingFilterProxy{
    private static Logger _log = LoggerFactory.getLogger(ShiroFilter.class);
    
    private static ThreadLocal<UpmsSession> redisSessionLocal = new ThreadLocal<UpmsSession>();// 缓存session，避免一次请求多次读取

    public static void setLocalSession(UpmsSession session) {
        redisSessionLocal.set(session);
    }

    public static boolean isLocalSession() {
        return redisSessionLocal.get() != null;
    }

    public static UpmsSession getLocalSession() {
        return redisSessionLocal.get();
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!ServletUtil.isStaticFile(request)) {
            HttpServletRequest req = (HttpServletRequest) request;
            String paramStr;
            if (req.getMethod().equalsIgnoreCase("GET")) {
                paramStr = req.getQueryString();
            } else {
                paramStr = ObjectUtils.toString(request.getParameterMap());
            }
            _log.info("request>>> from:{} uri:{} method:{} param:{}", RequestUtil.getIpAddr(req), req.getRequestURI(), req.getMethod(), paramStr);
            super.doFilter(request, response, filterChain);
        }else {
            filterChain.doFilter(request, response);
        }
        redisSessionLocal.remove();
    }
}
