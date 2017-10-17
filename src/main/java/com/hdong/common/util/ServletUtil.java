package com.hdong.common.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Servelt 工具类
 * 
 * @author hdong
 *
 */
public class ServletUtil {
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static boolean isStaticFile(HttpServletRequest request) {
        if (request != null) {
            String uri = request.getRequestURI();
            return uri.indexOf("resources") > 0 ? true : false;
        } else {
            return false;
        }
    }

    public static boolean isStaticFile(ServletRequest request) {
        if (request != null && request instanceof HttpServletRequest) {
            String uri = ((HttpServletRequest) request).getRequestURI();
            return uri.indexOf("resources") > 0 ? true : false;
        } else {
            return false;
        }
    }
}
