package com.southeast.ioc.factory;

import com.southeast.BeanPostProcessor;
import com.southeast.ioc.bean.BeanDefinition;
import com.southeast.ioc.bean.BeanReference;
import com.southeast.ioc.bean.PropertyValue;
import com.southeast.ioc.bean.PropertyValues;
import com.southeast.ioc.xml.XmlBeanDefinitionReader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 18351 on 2019/1/17.
 */
public class XmlBeanFactory implements BeanFactory{
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private List<String> beanDefinitionNames = new ArrayList<>();

    // 用来存储注册的BeanPostProcessor
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    private XmlBeanDefinitionReader beanDefinitionReader;

    //初始化的时候，自动装配属性
    public XmlBeanFactory(String location) throws Exception {
        beanDefinitionReader = new XmlBeanDefinitionReader();
        loadBeanDefinitions(location);
    }

    private void loadBeanDefinitions(String location) throws Exception {
        beanDefinitionReader.loadBeanDefinitions(location);
        registerBeanDefinition();
        registerBeanPostProcessor();
    }

    /**
     * 注册BeanDefinition
     */
    private void registerBeanDefinition() {
        //获取从xml文件中读取到的信息
        Map<String, BeanDefinition> map=beanDefinitionReader.getRegistry();
        for(String name:map.keySet()){
            BeanDefinition beanDefinition=map.get(name);
            beanDefinitionMap.put(name,beanDefinition);
            beanDefinitionNames.add(name);
        }
    }

    /**
     * 注册BeanPostProcessor
     */
    public void registerBeanPostProcessor() throws Exception {
        List beans = getBeansForType(BeanPostProcessor.class);
        for (Object bean : beans) {
            addBeanPostProcessor((BeanPostProcessor) bean);
        }
    }

    /**
     *  注册BeanPostProcessor
     *  1. 根据 BeanDefinition 记录的信息，寻找所有实现了 BeanPostProcessor 接口的类。
     *  2. 实例化 BeanPostProcessor 接口的实现类
     *  3. 将实例化好的对象放入 List中
     */
    private List getBeansForType(Class type) throws Exception {
        List beans = new ArrayList<>();
        for (String beanDefinitionName : beanDefinitionNames) {
            if (type.isAssignableFrom(beanDefinitionMap.get(beanDefinitionName).getBeanClass())) {
                beans.add(getBean(beanDefinitionName));
            }
        }
        return beans;
    }

    //将beanPostProcessor放入容器中
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * getBean 简化方案
     */
    @Override
    public Object getBean(String name) throws Exception {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (beanDefinition == null) {
            throw new IllegalArgumentException("no this bean with name " + name);
        }

        //根据beanDefiniton获取Bean
        Object bean = beanDefinition.getBean();
        if (bean == null) {
            //1. 实例化 Bean 对象
            //2.将配置文件中配置的属性注入到新创建的 Bean 对象中
            //3 .检查 Bean 对象是否实现了 Aware 一类的接口，如果实现了则把相应的依赖设置到 Bean 对象中。
            bean = createBean(beanDefinition);

            //4. 调用 BeanPostProcessor 前置处理方法，即 postProcessBeforeInitialization(Object bean, String beanName)
            //5. 调用 BeanPostProcessor 后置处理方法，即 postProcessAfterInitialization(Object bean, String beanName)
            bean = initializeBean(bean, name);
            beanDefinition.setBean(bean);
        }
        return bean;
    }

    private Object createBean(BeanDefinition bd) throws Exception {
        //1. 实例化 Bean 对象
        Object bean = bd.getBeanClass().newInstance();
        // 2. 将配置文件中配置的属性注入到新创建的 Bean 对象中
        applyPropertyValues(bean, bd);

        return bean;
    }

    private void applyPropertyValues(Object bean, BeanDefinition bd) throws Exception {
        //3 .检查 Bean 对象是否实现了 Aware 一类的接口，如果实现了则把相应的依赖设置到 Bean 对象中。
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
        PropertyValues propertyValues=bd.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getName());
            }

            //保证了使用 set方法为对象注入属性
            try {
                Method declaredMethod = bean.getClass().getDeclaredMethod(
                        "set" + propertyValue.getName().substring(0, 1).toUpperCase()
                                + propertyValue.getName().substring(1), value.getClass());
                declaredMethod.setAccessible(true);
                //执行 setter方法
                declaredMethod.invoke(bean, value);
            } catch (NoSuchMethodException e) {
                Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
                declaredField.setAccessible(true);
                //创建 setter
                declaredField.set(bean, value);
            }
        }
    }

    private Object initializeBean(Object bean, String name) throws Exception {
        //4. 调用 BeanPostProcessor 前置处理方法，即 postProcessBeforeInitialization(Object bean, String beanName)
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessBeforeInitialization(bean, name);
        }

        //5. 调用 BeanPostProcessor 后置处理方法，即 postProcessAfterInitialization(Object bean, String beanName)
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessAfterInitialization(bean, name);
        }
        return bean;
    }
}
