package jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by 18351 on 2019/1/15.
 */
public class JdkProxy implements InvocationHandler{
    private IUserDao iUserDao;

    public JdkProxy(IUserDao iUserDao){
        this.iUserDao=iUserDao;
    }

    public IUserDao getPrxoy(){
        return (IUserDao)Proxy.newProxyInstance(
                iUserDao.getClass().getClassLoader(),
                iUserDao.getClass().getInterfaces(),
                this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        Object obj = null;
        //只对add方法进行增强
        if ("add".equals(methodName)) {
            System.out.println("开启事务");
            obj = method.invoke(iUserDao, args);
            System.out.println("结束事务");
        } else {
            obj = method.invoke(iUserDao, args);
        }
        return obj;
    }
}
