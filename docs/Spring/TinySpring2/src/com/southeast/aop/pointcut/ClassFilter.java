package com.southeast.aop.pointcut;

/**
 * 类过滤器
 * 可以认为是Joinpoint(连接点)
 */
public interface ClassFilter {
    boolean matches(Class beanClass) throws Exception;
}
