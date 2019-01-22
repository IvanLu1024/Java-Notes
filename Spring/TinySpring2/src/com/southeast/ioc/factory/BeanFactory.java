package com.southeast.ioc.factory;



/**
 * Bean的容器
 *
 * XmlBeanDefinitionReader 在完成解析工作后，
 * BeanFactory 会将它解析得到的 <id, BeanDefinition>
 * 键值对注册到自己的 beanDefinitionMap 中。
 * BeanFactory 注册好 BeanDefinition 后，就立即开始注册 BeanPostProcessor 相关实现类。
 */
public interface BeanFactory {
    Object getBean(String name) throws Exception;
}
