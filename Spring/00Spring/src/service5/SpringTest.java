package service5;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by 18351 on 2019/1/9.
 */
public class SpringTest {
    @Test
    public void test(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "applicationContext5.xml");

        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.sayHello();
        System.out.println(userService);

        UserService userService2 = (UserService) applicationContext.getBean("userService");
        userService2.sayHello();
        System.out.println(userService2);

        applicationContext.close();
    }
}
