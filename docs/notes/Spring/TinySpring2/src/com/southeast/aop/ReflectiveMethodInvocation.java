package com.southeast.aop;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;


 /**
 * 反射型方法拦截器
 * 在方法调用的时候进行拦截
 */
public class ReflectiveMethodInvocation implements MethodInvocation {
    protected Object target;

    protected Method method;

    protected Object[] arguments;

    public ReflectiveMethodInvocation(Object target, Method method, Object[] arguments) {
        this.target = target;
        this.method = method;
        this.arguments = arguments;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    /**
     * proceed()
     * 方法是调用原始对象的方法 method.invoke(object,args)。
     */
    @Override
    public Object proceed() throws Throwable {
        return method.invoke(target, arguments);
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }
}
