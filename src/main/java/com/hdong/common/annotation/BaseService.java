package com.hdong.common.annotation;

import java.lang.annotation.*;

/**
 * 初始化继承BaseService的service
 * Created by hdong on 2017-7-25.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BaseService {
}
