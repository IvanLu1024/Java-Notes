package service;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

/**
 * Created by 18351 on 2019/1/9.
 */
public class SpringTest {
    /**
     * 传统方式
     */
    @Test
    public void test(){
        UserService userService=new UserServiceImpl();
        userService.say();
    }


    /**
     * 创建一个工厂类.使用工厂类来创造对象
     */
    @Test
    public void test2(){
        // 创建一个工厂类
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService=(UserService)applicationContext.getBean("userService");
        userService.say();
    }

    /**
     *加载磁盘路径下的配置文件
     */
    @Test
    public void test3() {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
                "applicationContext.xml");
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.say();
    }

    @Test
    public void test4(){
        // ClassPathResource  FileSystemResource
        BeanFactory beanFactory = new XmlBeanFactory(new FileSystemResource("applicationContext.xml"));
        UserService userService = (UserService)  beanFactory.getBean("userService");
        userService.say();
    }
}
