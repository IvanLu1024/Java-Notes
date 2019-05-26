package com.southeast.ioc.xml;

/**
 * 读取配置文件中信息，封装到 BeanDefinition
 */
public interface BeanDefinitionReader {
    void loadBeanDefinitions(String location) throws Exception;
}
