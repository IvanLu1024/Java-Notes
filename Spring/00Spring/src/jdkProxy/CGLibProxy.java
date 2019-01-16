package jdkProxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by 18351 on 2019/1/15.
 */
public class CGLibProxy implements MethodInterceptor {
    private ProductDao productDao;

    public CGLibProxy(ProductDao productDao) {
        this.productDao = productDao;
    }

    public ProductDao getProxy(){
        // 使用CGLIB生成代理:
        // 1.创建核心类:
        Enhancer enhancer = new Enhancer();
        // 2.为其设置父类:
        enhancer.setSuperclass(productDao.getClass());
        // 3.设置回调:
        enhancer.setCallback(this);
        // 4.创建代理:
        return (ProductDao) enhancer.create();
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        String menthodName=method.getName();
        Object obj=null;
        if("add".equals(menthodName)){
            System.out.println("开启事务");
            obj=methodProxy.invokeSuper(proxy,args);
            System.out.println("结束事务");
        }else{
            obj=methodProxy.invokeSuper(proxy,args);
        }
        return obj;
    }
}
