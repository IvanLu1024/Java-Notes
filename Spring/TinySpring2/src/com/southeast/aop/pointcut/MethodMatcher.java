package com.southeast.aop.pointcut;

import java.lang.reflect.Method;

/**
 * 方法匹配器
 * 可以认为是Joinpoint(连接点)
 */
public interface MethodMatcher {
    /**
     * 匹配该方法是否是要拦截的方法
     */
    boolean matches(Method method, Class targetClass);
}
