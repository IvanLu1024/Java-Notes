package com.southeast;

import com.southeast.aop.AdvisedSupport;
import com.southeast.aop.proxy.AopProxy;
import com.southeast.aop.proxy.JDKDynamicAopProxy;

/**
 * Created by 18351 on 2019/1/19.
 */
public class ProxyFactory extends AdvisedSupport implements AopProxy {
    @Override
    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        return new JDKDynamicAopProxy(this);
    }
}
