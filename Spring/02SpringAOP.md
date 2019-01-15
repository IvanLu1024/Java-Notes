<!-- GFM-TOC -->
* [SpringAOP](#SpringAOP)
    * [SpringAOP概述](#SpringAOP概述)
    * [AOP的底层实现](#AOP的底层实现)
    * [Spring中的AOP](#Spring中的AOP)
    * [Spring的AspectJ的AOP](#Spring的AspectJ的AOP)
<!-- GFM-TOC -->

# SpringAOP

## SpringAOP概述
### 什么是AOP
AOP(面向切面编程,Aspect Oriented Programing)。

AOP采取**横向抽取**机制，取代了传统纵向继承体系重复性代码（性能监视、事务管理、安全检查、缓存）

Spring AOP使用纯Java实现，不需要专门的编译过程和类加载器，在运行期通过代理方式向目标类**织入**增强代码

AspecJ是一个基于Java语言的AOP框架，Spring2.0开始，Spring AOP引入对AspectJ的支持，
AspectJ扩展了Java语言，提供了一个专门的编译器，在编译时提供横向代码的织入。

AOP底层原理就是**代理机制**。

### Spring的AOP代理
- JDK动态代理:对实现了接口的类生成代理
- CGLib代理机制:对类生成代理

### AOP的术语

| 术语 | 描述 |
| :--: | :--: |
| Joinpoint(连接点) | 所谓连接点是指那些被拦截到的点。在Spring中,这些点指的是**方法**,因为Spring只支持**方法类型的连接点**。 | 
| Pointcut(切入点) | 所谓切入点是指我们要**对哪些Joinpoint进行拦截**的定义。 | 
| Advice(通知/增强) | 所谓通知是指拦截到Joinpoint之后所要做的事情就是**通知**。通知分为前置通知,后置通知,异常通知,最终通知,环绕通知(切面要完成的功能) | 
| Introduction(引介) | 引介是一种**特殊的通知**在不修改类代码的前提下, Introduction可以在运行期为类动态地添加一些方法或Field | 
| Target(目标对象) | 代理的目标对象 | 
| Weaving(织入) | 是指把增强应用到目标对象来创建新的代理对象的过程。 Spring采用**动态代理织入**，而AspectJ采用**编译期织入**和**类装载期织入** | 
| Proxy（代理）| 一个类被AOP织入增强后，就产生一个结果代理类 | 
| Aspect(切面) | 是切入点和通知（引介）的结合 | 

<div align="center"><img src="pics\\02_1.bmp"/></div>

## AOP的底层实现
### JDK动态代理
JDK动态代理:对**实现了接口的类**生成代理

```java
public interface IUserDao {
    void add();
    void delete();
    void update();
    void search();
}
```
- UserDao实现了IUserDao接口
```java
public class UserDao implements IUserDao{
    @Override
    public void add() {
        System.out.println("添加功能");
    }

    @Override
    public void delete() {
        System.out.println("删除功能");
    }

    @Override
    public void update() {
        System.out.println("更新功能");
    }

    @Override
    public void search() {
        System.out.println("查找功能");
    }
}
```

```java
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
```
- 对实现了接口的类UserDao生成代理
```java
public class JdkProxyDemo {
    public static void main(String[] args) {
        IUserDao userDao=new UserDao();
        JdkProxy jdkProxy=new JdkProxy(userDao);
        IUserDao userDao2=jdkProxy.getPrxoy();
        userDao2.add();
    }
}
```
- 输出结果
```html
开启事务
添加功能
结束事务
```

### Cglib动态代理
CGLIB(Code Generation Library)是一个开源项目！
是一个强大的，高性能，高质量的Code生成类库，它可以在运行期扩展Java类与实现Java接口。

```java
public class ProductDao {
    public void add() {
        System.out.println("添加功能");
    }
    
    public void delete() {
        System.out.println("删除功能");
    }
    
    public void update() {
        System.out.println("更新功能");
    }
    
    public void search() {
        System.out.println("查找功能");
    }
}
```

```java
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
```
```java
public class CGLibProxyDemo {
    public static void main(String[] args) {
        ProductDao productDao=new ProductDao();
        CGLibProxy proxy=new CGLibProxy(productDao);
        ProductDao productDao2=proxy.getProxy();
        productDao2.add();
    }
}
```
- 输出结果
```html
开启事务
添加功能
结束事务
```

**Spring框架,如果类实现了接口,就使用JDK的动态代理生成代理对象,
如果这个类没有实现任何接口,使用CGLIB生成代理对象。**


相关阅读

- [代理设计模式](https://github.com/DuHouAn/Java/blob/master/Object_Oriented/notes/03%E7%BB%93%E6%9E%84%E5%9E%8B.md#7-%E4%BB%A3%E7%90%86proxy)

## Spring中的AOP


## Spring的AspectJ的AOP
