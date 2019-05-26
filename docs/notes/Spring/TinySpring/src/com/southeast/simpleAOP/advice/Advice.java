package com.southeast.simpleAOP.advice;


import java.lang.reflect.InvocationHandler;

/**
 * 所谓通知(增强)是指拦截到Joinpoint之后所要做的事情就是通知。
 */
public interface Advice extends InvocationHandler {
}
