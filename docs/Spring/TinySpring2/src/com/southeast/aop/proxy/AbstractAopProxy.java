package com.southeast.aop.proxy;

import com.southeast.aop.AdvisedSupport;

public abstract class AbstractAopProxy implements AopProxy{
    // AdvisedSupport封装了TargetSource 、MethodInterceptor、MethodMatcher
    protected AdvisedSupport advised;

    public AbstractAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }
}
