package service2;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by 18351 on 2019/1/9.
 */
public class SpringTest {
    @Test
    // 无参数的构造方法的实例化
    public void demo1() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "applicationContext2.xml");
        Bean1 bean1 = (Bean1) applicationContext.getBean("bean1");
        System.out.println(bean1);
    }

    @Test
    // 静态工厂实例化
    public void demo2() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "applicationContext2.xml");
        Bean2 bean2 = (Bean2) applicationContext.getBean("bean2");
        System.out.println(bean2);
    }

    @Test
    // 实例工厂实例化
    public void demo3() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "applicationContext2.xml");
        Bean3 bean3 = (Bean3) applicationContext.getBean("bean3");
        System.out.println(bean3);
    }
}
