package com.southeast.simpleAOP.advice;

import java.lang.reflect.Method;

/**
 * Created by 18351 on 2019/1/16.
 */
public class BeforeAdvice implements Advice{
    private Object bean;
    private MethodInvocation methodInvocation;

    public BeforeAdvice(Object bean,MethodInvocation methodInvocation){
        this.bean = bean;
        this.methodInvocation = methodInvocation;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        methodInvocation.invoke();
        //在方法代码执行前进行了增强
        Object obj=method.invoke(bean,args);
        return obj;
    }
}
