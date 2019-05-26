package com.southeast.aop.pointcut;

/**
 * 切点：
 * 这里就是要拦截的方法和相关类
 */
public interface Pointcut {
    ClassFilter getClassFilter();
    MethodMatcher getMethodMatcher();
}
