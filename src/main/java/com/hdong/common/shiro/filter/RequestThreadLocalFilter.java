package com.hdong.common.shiro.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hdong.common.shiro.session.UpmsSession;
import com.hdong.common.util.ServletUtil;
import com.hdong.common.web.util.RequestUtil;

/**
 * shiro多次读取 更新redis的数据，该程序是设置缓存，避免过多的读取redis Created by hdong on 2017/3/14.
 */
@WebFilter(filterName = "RequestThreadLocalFilter", urlPatterns = "/*")
public class RequestThreadLocalFilter implements Filter {

    private static Logger _log = LoggerFactory.getLogger(RequestThreadLocalFilter.class);

    private static ThreadLocal<UpmsSession> redisSessionLocal = new ThreadLocal<UpmsSession>();// 缓存session，避免一次请求多次读取

    private static ThreadLocal<Boolean> controllerReq = new ThreadLocal<Boolean>();

    public static void setLocalSession(UpmsSession session) {
        redisSessionLocal.set(session);
    }

    public static boolean isLocalSession() {
        return redisSessionLocal.get() != null;
    }

    public static UpmsSession getLocalSession() {
        return redisSessionLocal.get();
    }

    public static boolean isControllerReq() {
        return controllerReq.get();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!ServletUtil.isStaticFile(request)) {
            controllerReq.set(true);
            // 获取request
            HttpServletRequest req = (HttpServletRequest) request;
            String paramStr;
            if (req.getMethod().equalsIgnoreCase("GET")) {
                paramStr = req.getQueryString();
            } else {
                paramStr = ObjectUtils.toString(request.getParameterMap());
            }
            _log.info("request>>> from:{} uri:{} method:{} param:{}", RequestUtil.getIpAddr(req), req.getRequestURI(), req.getMethod(), paramStr);
        } else {
            controllerReq.set(false);
        }
        chain.doFilter(request, response);
        redisSessionLocal.remove();
        controllerReq.remove();
    }

    @Override
    public void destroy() {}
}
