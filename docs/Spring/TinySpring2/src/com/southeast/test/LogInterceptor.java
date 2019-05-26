package com.southeast.test;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * LogIntercepter方法拦截器
 */
public class LogInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println(invocation.getMethod().getName() + " method start");
        Object obj= invocation.proceed();
        System.out.println(invocation.getMethod().getName() + " method end");
        return obj;
    }
}
