package com.southeast.simpleAOP.factory;

import com.southeast.simpleAOP.advice.Advice;

import java.lang.reflect.Proxy;

/**
 * Created by 18351 on 2019/1/16.
 */
public class BeanFactory {
    public static Object getProxy(Object bean, Advice advice) {
        return Proxy.newProxyInstance(
                bean.getClass().getClassLoader(),
                bean.getClass().getInterfaces(),
                advice);
    }
}
