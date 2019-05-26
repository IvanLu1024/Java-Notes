package com.southeast;

/**
 * BeanPostProcessor 接口是Spring 对外拓展的接口之一，
 * 其主要用途提供一个机会，让开发人员能够控制 bean 的实例化过程。
 **通过实现这个接口，我们就可在 Bean 实例化时，对 Bean 进行一些处理。
 */
public interface BeanPostProcessor {
    Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception;
    Object postProcessAfterInitialization(Object bean, String beanName) throws Exception;
}
