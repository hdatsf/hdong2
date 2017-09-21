package com.hdong.common.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Servelt 工具类
 * @author hdong
 *
 */
public class ServletUtil {
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(requestAttributes == null) {
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();  
    }  
  
    public static boolean isStaticFile(String uri) {  
        return uri.indexOf("resources")>0?true:false;
    }  
}
