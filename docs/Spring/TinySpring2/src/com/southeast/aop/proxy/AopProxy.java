package com.southeast.aop.proxy;

/**
 * 代理对象生成器接口
 */
public interface AopProxy {
    /**
     * 为目标 Bean 生成代理对象
     * */
    Object getProxy();
}
