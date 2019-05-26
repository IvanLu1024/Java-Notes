package com.southeast.simpleAOP;

import com.southeast.simpleAOP.advice.Advice;
import com.southeast.simpleAOP.advice.BeforeAdvice;
import com.southeast.simpleAOP.advice.MethodInvocation;
import com.southeast.simpleAOP.bean.IUserService;
import com.southeast.simpleAOP.bean.UserService;
import com.southeast.simpleAOP.factory.BeanFactory;
import org.junit.Test;

/**
 * Created by 18351 on 2019/1/16.
 */
public class BeanFactoryTest {
    @Test
    public void test(){
        // 1. 创建一个 MethodInvocation 实现类
        //MethodInvocation logTask = () -> System.out.println("log task start");
        MethodInvocation logTask = new MethodInvocation() {
            @Override
            public void invoke() {
                System.out.println("log task start");
            }
        };

        // 2. 创建一个 Advice
        UserService userService=new UserService();
        Advice beforeAdvice = new BeforeAdvice(userService, logTask);

        // 3. 为目标对象生成代理 -->这里使用JDK动态代理-->对接口进行代理
        IUserService proxy = (IUserService) BeanFactory.getProxy(userService,beforeAdvice);

        proxy.sayHello();
    }
}
