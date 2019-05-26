package com.southeast.simpleIOC;

import com.southeast.simpleIOC.bean.Car;
import com.southeast.simpleIOC.bean.Wheel;
import com.southeast.simpleIOC.factory.BeanFactory;
import org.junit.Test;

/**
 * 测试简单IOC
 */
public class BeanFactoryTest {
    @Test
    public void test() throws Exception {
        BeanFactory beanFactory=new BeanFactory("applicationContext.xml");
        Car car=(Car)beanFactory.getBean("car");
        System.out.println(car);
        Wheel wheel=(Wheel)beanFactory.getBean("wheel");
        System.out.println(wheel);
   }
}
