package jdkProxy;

/**
 * Created by 18351 on 2019/1/15.
 */
public class JdkProxyDemo {
    public static void main(String[] args) {
        IUserDao userDao=new UserDao();
        JdkProxy jdkProxy=new JdkProxy(userDao);
        IUserDao userDao2=jdkProxy.getPrxoy();
        userDao2.add();
    }
}
