package com.southeast;

import com.southeast.ioc.factory.XmlBeanFactory;
import com.southeast.test.IUserDao;
import com.southeast.test.UserDao;
import org.testng.annotations.Test;

/**
 * Created by 18351 on 2019/1/19.
 */
public class XmlBeanFactoryTest {
    @Test
    public void getBean() throws Exception {
        System.out.println("--------- AOP test ----------");
        String location = getClass().getClassLoader().getResource("spring.xml").getFile();
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory(location);
        //UserDao userDao = (UserDao) xmlBeanFactory.getBean("userDao");
        //userDao.add();
    }
}
