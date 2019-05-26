package com.southeast.ioc.factory;

/**
 * Created by 18351 on 2019/1/17.
 */
public interface BeanFactoryAware {
    void setBeanFactory(BeanFactory beanFactory) throws Exception;
}
