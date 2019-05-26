package com.southeast.aop.pointcut;

import org.aopalliance.aop.Advice;

/**
 * Advisor 是 Spring中传统切面接口。
 */
public interface Advisor {
    Advice getAdvice();
}
