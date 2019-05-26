package com.southeast.aop.proxy;

import com.southeast.aop.AdvisedSupport;
import com.southeast.aop.pointcut.MethodMatcher;
import com.southeast.aop.ReflectiveMethodInvocation;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 基于 JDK 动态代理的代理对象生成器
 */
public class JDKDynamicAopProxy extends AbstractAopProxy implements InvocationHandler{
    public JDKDynamicAopProxy(AdvisedSupport advised) {
        super(advised);
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(getClass().getClassLoader(),
                advised.getTargetSource().getInterfaces(),this);
    }

    @Override
    public Object invoke(final Object proxy, Method method, final Object[] args) throws Throwable {
        Object target=advised.getTargetSource().getTarget();
        MethodMatcher methodMatcher = advised.getMethodMatcher();

        /**
         * 1. 使用方法匹配器 methodMatcher 测试 Bean 中原始方法 method 是否符合匹配规则
         */
        if (methodMatcher != null && methodMatcher.matches(method, target.getClass())) {
            // 获取 Advice (通知)。
            // MethodInterceptor 的父接口继承了 Advice
            MethodInterceptor methodInterceptor = advised.getMethodInterceptor();

           /**
            * 2. 将 Bean 的原始方法 method 封装在 MethodInvocation 接口实现类对象中，
            * 并把生成的对象作为参数传给 Adivce 实现类对象，执行通知逻辑
            */
            return methodInterceptor.invoke(
                    new ReflectiveMethodInvocation(target, method, args));
        } else {
            // 2. 当前 method 不符合匹配规则，直接调用 Bean 的原始方法 method
            return method.invoke(target, args);
        }
    }
}
