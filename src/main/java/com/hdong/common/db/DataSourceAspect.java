package com.hdong.common.db;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

@Aspect
@Order(1)
public class DataSourceAspect {
    @Before(value = "@annotation(com.hdong.common.db.DataSource)")
    public void before(JoinPoint jp) {
        Object obj = jp.getTarget();
        String methodName = jp.getSignature().getName();
        Class<?>[] argTypes = ((MethodSignature)jp.getSignature()).getMethod().getParameterTypes();
        try {
            Method method = obj.getClass().getMethod(methodName, argTypes);
            DataSourceEnum ds = method.getAnnotation(DataSource.class).name();
            DynamicDataSource.setDataSource(ds);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    // 声明最终通知
    @After(value = "@annotation(com.hdong.common.db.DataSource)")
    public void doAfter() {
        DynamicDataSource.clearDataSource();
    }
}
