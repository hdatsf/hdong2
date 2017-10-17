package com.hdong.common.shiro.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;


/**
 * shiroFilter
 * Created by hdong on 2017/6/24.
 */
@WebFilter(
        filterName = "shiroFilter",
        urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "targetFilterLifecycle", value = "true")
        }
)
public class ShiroFilter extends org.springframework.web.filter.DelegatingFilterProxy {
    
}
