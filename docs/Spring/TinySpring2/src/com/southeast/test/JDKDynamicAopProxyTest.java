package com.southeast.test;

import com.southeast.aop.AdvisedSupport;
import com.southeast.aop.pointcut.MethodMatcher;
import com.southeast.aop.TargetSource;
import com.southeast.aop.proxy.JDKDynamicAopProxy;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

/**
 * Created by 18351 on 2019/1/18.
 */
public class JDKDynamicAopProxyTest {
    @Test
    public void test(){
        System.out.println("---------- no proxy ----------");
        IUserDao userDao= new UserDao();
        userDao.add();
        userDao.delete();

        System.out.println("\n----------- proxy -----------");
        AdvisedSupport advised = new AdvisedSupport();
        TargetSource targetSource = new TargetSource(
                userDao,UserDao.class,IUserDao.class);
        advised.setTargetSource(targetSource);
        advised.setMethodInterceptor(new LogInterceptor());

        //仅仅对add方法进行匹配
        advised.setMethodMatcher(
                new MethodMatcher() {
                    @Override
                    public boolean matches(Method method, Class targetClass) {
                        if("add".equals(method.getName())){
                            return true;
                        }
                        return false;
                    }
                }
        );

        userDao = (IUserDao)new JDKDynamicAopProxy(advised).getProxy();
        userDao.add();
        userDao.delete();
    }
}
