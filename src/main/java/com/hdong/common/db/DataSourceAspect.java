package com.hdong.common.db;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

@Aspect
@Order(1)
public class DataSourceAspect {
    @Before(value = "@annotation(com.hdong.common.db.DataSource)")
    public void before(JoinPoint jp) {
        Object obj = jp.getTarget();
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        Class<?>[] argTypes = new Class[args.length];
        int i=0;
        for(Object arg : args) {
            argTypes[i++] = arg.getClass();
        }
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
