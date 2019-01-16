package jdkProxy;

/**
 * Created by 18351 on 2019/1/15.
 */
public class CGLibProxyDemo {
    public static void main(String[] args) {
        ProductDao productDao=new ProductDao();
        CGLibProxy proxy=new CGLibProxy(productDao);
        ProductDao productDao2=proxy.getProxy();
        productDao2.add();
    }
}
