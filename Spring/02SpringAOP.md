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
| Aspect(切面) | 是切入点和通知（/引介）的结合 | 

- 示例：

<div align="center"><img src="pics\\02_1.png" width="800"/></div>

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

### 总结

Spring框架中：

- **如果类实现了接口,就使用JDK的动态代理生成代理对象**
- **如果这个类没有实现任何接口,使用CGLIB生成代理对象**


### 相关阅读

- [代理设计模式](https://github.com/DuHouAn/Java/blob/master/Object_Oriented/notes/03%E7%BB%93%E6%9E%84%E5%9E%8B.md#7-%E4%BB%A3%E7%90%86proxy)

## Spring中的AOP

### Spring中通知
Spring中的通知Advice其实是指“增强代码”。

| 通知类型 | 全类名 | 说明 |
| :--: | :--: | :--: |
| 前置通知 | org.springframework.aop.MethodBeforeAdvice | 在目标方法执行前实施增强 |
| 后置通知 | org.springframework.aop.AfterReturningAdvice | 在目标方法执行后实施增强 |
| 环绕通知 |  org.aopalliance.intercept.MethodInterceptor | 在目标方法执行前后实施增强 |
| 异常抛出通知 | org.springframework.aop.ThrowsAdvice | 在方法抛出异常后实施增强 |
| 引介通知 | org.springframework.aop.IntroductionInterceptor | 在目标类中添加一些新的方法和属性 |


### Spring中切面类型
Advisor : Spring中传统切面。
- Advisor:一个切点和一个通知组合。
- Aspect:多个切点和多个通知组合。

Advisor : 代表一般切面，Advice本身就是一个切面，对目标类所有方法进行拦截(不带有切点的切面.针对所有方法进行拦截)
PointcutAdvisor : 代表具有切点的切面，可以指定拦截目标类哪些方法(带有切点的切面,针对某个方法进行拦截)
IntroductionAdvisor : 代表引介切面，针对引介通知而使用切面（不要求掌握）

### Spring的AOP的开发

#### 1. 不带有切点的切面(针对所有方法的增强)
> 第一步:导入相应jar包
```html
<dependency>
  <groupId>aopalliance</groupId>
  <artifactId>aopalliance</artifactId>
  <version>1.0</version>
</dependency>
```

> 第二步:编写被代理对象

IUserDao接口
```java
public interface IUserDao {
    void add();
    void update();
    void delete();
    void search();
}
```
UserDao实现类
```java
public class UserDao implements IUserDao{
    @Override
    public void add() {
        System.out.println("增加功能");
    }

    @Override
    public void update() {
        System.out.println("修改功能");
    }

    @Override
    public void delete() {
        System.out.println("删除功能");
    }

    @Override
    public void search() {
        System.out.println("查找功能");
    }
}
```

> 第三步:编写增强的代码
```java
import org.springframework.aop.MethodBeforeAdvice;
import java.lang.reflect.Method;

/**
 * 前置增强
 */
public class MyBeforeAdvice implements MethodBeforeAdvice{
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("前置增强...");
    }
}
```

> 第四步:生成代理(配置生成代理)

**生成代理Spring基于ProxyFactoryBean类。底层自动选择使用JDK的动态代理还是CGLIB的代理**。

属性:
- target : 代理的目标对象
- proxyInterfaces : 代理要实现的接口
```html
如果多个接口可以使用以下格式赋值
<list>
    <value></value>
    ....
</list>
```
- proxyTargetClass : 是否对类代理而不是接口，设置为true时，使用CGLib代理
- interceptorNames : 需要织入目标的Advice
- singleton : 返回代理是否为单实例，默认为单例
- optimize : 当设置为true时，强制使用CGLib

```html
<!-- 不带有切点的切面 -->
<!-- 定义目标对象 -->
<bean id="userDao" class="advice.UserDao"></bean>

<!-- 定义增强 -->
<bean id="beforeAdvice" class="advice.MyBeforeAdvice"></bean>

<!-- Spring支持配置生成代理: -->
<bean id="customerDaoProxy" class="org.springframework.aop.framework.ProxyFactoryBean">
    <!-- 设置目标对象 -->
    <property name="target" ref="userDao"/>
    <!-- 设置实现的接口 ,value中写接口的全路径 -->
    <property name="proxyInterfaces" value="advice.IUserDao"/>
    <!-- 需要使用value:要的名称 -->
    <property name="interceptorNames" value="beforeAdvice"/>
    <!-- 强制使用CGLIB代理 -->
    <property name="optimize" value="true"></property>
</bean>
```
> 测试
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringTest {
    @Autowired
    @Qualifier("userDaoProxy")
    // 注入是真实的对象,必须注入代理对象.
    IUserDao iUserDao;

    @Test
    public void test(){
        iUserDao.add();
        iUserDao.delete();
        iUserDao.update();
        iUserDao.search();
    }
}
```
- 输出结果：
```html
前置增强...
增加功能
前置增强...
删除功能
前置增强...
修改功能
前置增强...
查找功能
```

#### 2. 带有切点的切面(针对目标对象的某些方法进行增强)
PointcutAdvisor 接口:
- DefaultPointcutAdvisor 最常用的切面类型，它可以通过任意Pointcut和Advice组合定义切面
- RegexpMethodPointcutAdvisor 构造正则表达式切点切面

> 第一步:创建被代理对象
```java
public class OrderDao {
    public void add() {
        System.out.println("增加功能");
    }

    public void update() {
        System.out.println("修改功能");
    }

    public void delete() {
        System.out.println("删除功能");
    }

    public void search() {
        System.out.println("查找功能");
    }
}
```

> 第二步:编写增强的代码

```java
/**
 * 环绕增强
 */
public class MyAroundAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("环绕前增强...");
        Object obj= invocation.proceed();
        System.out.println("环绕后增强...");
        return obj;
    }
}
```

> 第三步:生成代理(配置生成代理)

```html
<!-- 带有切点的切面 -->
<!-- 定义目标对象 -->
<bean id="orderDao" class="advice.OrderDao"></bean>

<!-- 定义增强 -->
<bean id="aroundAdvice" class="advice.MyAroundAdvice"></bean>

<!-- 定义切点切面: -->
<bean id="myPointcutAdvisor" class="org.springframework.aop.support.RegexpMethodPointcutAdvisor">
    <!-- 定义表达式,规定哪些方法执行拦截 -->
    <!-- . 任意字符  * 任意个 -->
    <!-- <property name="pattern" value=".*"/> -->
    <!-- <property name="pattern" value="cn\.itcast\.spring3\.demo4\.OrderDao\.add.*"/> -->
    <!-- <property name="pattern" value=".*add.*"></property> -->
    <property name="patterns" value=".*add.*,.*search.*"></property>
    <!-- 应用增强 -->
    <property name="advice" ref="aroundAdvice"/>
</bean>

<!-- 定义生成代理对象 -->
<bean id="orderDaoProxy" class="org.springframework.aop.framework.ProxyFactoryBean">
    <!-- 配置目标 -->
    <property name="target" ref="orderDao"></property>
    <!-- 针对类的代理 -->
    <property name="proxyTargetClass" value="true"></property>
    <!-- 在目标上应用增强 -->
    <property name="interceptorNames" value="myPointcutAdvisor"></property>
</bean>
```

> 测试
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringTest {
    @Autowired
    @Qualifier("orderDaoProxy")
    OrderDao orderDao;

    @Test
    public void test(){
        orderDao.add();
        orderDao.delete();
        orderDao.update();
        orderDao.search();
    }
}
```

- 输出结果：
```html
环绕前增强...
增加功能
环绕后增强...
删除功能
修改功能
环绕前增强...
查找功能
环绕后增强...
```

#### 3. 自动代理
前面的案例中，每个代理都是通过ProxyFactoryBean织入切面代理，
在实际开发中，非常多的**Bean每个都配置ProxyFactoryBean开发维护量巨大**。

自动创建代理(**基于后处理Bean。在Bean创建的过程中完成的增强。生成Bean就是代理**。)
- BeanNameAutoProxyCreator 根据Bean名称创建代理 
- DefaultAdvisorAutoProxyCreator 根据Advisor本身包含信息创建代理。
其中AnnotationAwareAspectJAutoProxyCreator是基于Bean中的AspectJ 注解进行自动代理。

**BeanNameAutoProxyCreator(按名称生成代理)**

```html
<!-- 定义目标对象 -->
<bean id="userDao" class="advice.UserDao"></bean>
<bean id="orderDao" class="advice.OrderDao"></bean>

<!-- 定义增强 -->
<bean id="beforeAdvice" class="advice.MyBeforeAdvice"></bean>
<bean id="aroundAdvice" class="advice.MyAroundAdvice"></bean>

<!-- 自动代理:按名称的代理 基于后处理Bean,后处理Bean不需要配置ID-->
<bean class="org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator">
    <property name="beanNames" value="*Dao"/>
    <property name="interceptorNames" value="beforeAdvice"/>
    <!-- 强制使用CGLIB代理 -->
    <property name="optimize" value="true"/>
</bean>
```

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringTest {
    @Autowired
    @Qualifier("userDao")
    UserDao userDao; 
    //代理的是实例类，不是接口。

    @Autowired
    @Qualifier("orderDao")
    OrderDao orderDao;

    @Test
    public void test(){
        userDao.add();
        userDao.search();

        orderDao.add();
        orderDao.search();
    }
}
```

**DefaultAdvisorAutoProxyCreator(根据切面中定义的信息生成代理)**

```html
<!-- 定义目标对象 -->
<bean id="userDao" class="advice.UserDao"></bean>
<bean id="orderDao" class="advice.OrderDao"></bean>

<!-- 定义增强 -->
<bean id="beforeAdvice" class="advice.MyBeforeAdvice"></bean>
<bean id="aroundAdvice" class="advice.MyAroundAdvice"></bean>

<!-- 定义一个带有切点的切面 -->
<bean id="myPointcutAdvisor" class="org.springframework.aop.support.RegexpMethodPointcutAdvisor">
    <!-- 对 add 方法进行增强-->
    <!--  <property name="pattern" value=".*add.*"/>-->

    <!-- 对 add、search 方法进行增强-->
    <property name="patterns">
        <list>
            <value>.*add.*</value>
            <value>.*search.*</value>
        </list>
    </property>

    <property name="advice" ref="aroundAdvice"/>
</bean>

<!-- 自动生成代理 -->
<bean class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator">
    <!-- 强制使用CGLIB代理 -->
    <property name="optimize" value="true"/>
</bean>
```

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringTest {
    @Autowired
    @Qualifier("userDao")
    IUserDao userDao;

    @Autowired
    @Qualifier("orderDao")
    OrderDao orderDao;

    @Test
    public void test(){
        userDao.add();
        userDao.search();

        orderDao.add();
        orderDao.search();
    }
}
```

### 区分基于ProxyFattoryBean的代理与自动代理区别?

- ProxyFactoryBean:先有被代理对象,将被代理对象传入到代理类中生成代理。

- 自动代理基于后处理Bean。**在Bean的生成过程中,就产生了代理对象**,把代理对象返回。
生成的Bean已经是代理对象。

## Spring的AspectJ的AOP

### 基于XML
> 第一步:编写被增强的类

UserDao

> 第二步:定义切面

```java
public class MyAspectXML {
    public void before(){
        System.out.println("前置通知...");
    }

    public void afterReturing(Object returnVal){
        System.out.println("后置通知...返回值:"+returnVal);
    }

    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        System.out.println("环绕前增强....");
        Object result = proceedingJoinPoint.proceed();
        System.out.println("环绕后增强....");
        return result;
    }

    public void afterThrowing(Throwable e){
        System.out.println("异常通知..."+e.getMessage());
    }

    public void after(){
        System.out.println("最终通知....");
    }
}
```

> 第三步:配置applicationContext.xml

```html
<!-- 定义被增强的类 -->
<bean id="userDao" class="advice.UserDao"></bean>

<!-- 定义切面 -->
<bean id="myAspectXML" class="advice.MyAspectXML"></bean>

<!-- 定义aop配置 -->
<aop:config>
    <!-- 定义切点: -->
    <aop:pointcut expression="execution(* advice.UserDao.add(..)) || execution(* advice.UserDao.search(..))" id="mypointcut"/>
    <aop:aspect ref="myAspectXML">
        <!-- 前置通知 -->
        <!-- <aop:before method="before" pointcut-ref="mypointcut"/> -->
        <!-- 后置通知 -->
        <!-- <aop:after-returning method="afterReturing" pointcut-ref="mypointcut" returning="returnVal"/> -->
        <!-- 环绕通知 -->
         <aop:around method="around" pointcut-ref="mypointcut"/>
        <!-- 异常通知 -->
        <!-- <aop:after-throwing method="afterThrowing" pointcut-ref="mypointcut" throwing="e"/> -->
        <!-- 最终通知 -->
        <!--<aop:after method="after" pointcut-ref="mypointcut"/>-->
    </aop:aspect>
</aop:config>
```

- 测试
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringTest {
    @Autowired
    @Qualifier("userDao")
    IUserDao userDao;

    @Test
    public void test(){
        userDao.add();
        userDao.search();
    }
}
```

### 基于注解
AspectJ的通知类型：

| 通知类型 | 解释 | 说明 |
| :--:| :--: | :--: |
| @Before |  前置通知，相当于BeforeAdvice | 就在方法之前执行。没有办法阻止目标方法执行的。 |
| @AfterReturning | 后置通知，相当于AfterReturningAdvice | 后置通知,获得方法返回值。 |
| @Around | 环绕通知，相当于MethodInterceptor | 在可以方法之前和之后来执行的,而且可以阻止目标方法的执行。 |
| @AfterThrowing | 抛出通知，相当于ThrowAdvice | |
| @After | 最终final通知，不管是否异常，该通知都会执行 | |
| @DeclareParents | 引介通知，相当于IntroductionInterceptor | |


**Advisor和Aspect的区别**?
- Advisor:Spring传统意义上的切面，支持一个切点和一个通知的组合。
- Aspect:可以支持多个切点和多个通知的组合。

> 使用注解编写切面类
```java
/**
 * 切面类:就是切点与增强结合
 */
@Aspect //申明是切面
public class MyAspect{
    @Before("execution(* advice.UserDao.add(..))")
    public void before(JoinPoint joinPoint){
        System.out.println("前置增强...."+joinPoint);
    }

    @AfterReturning(value="execution(* advice.UserDao.update(..))",returning="returnVal")
    public void afterReturin(Object returnVal){
        System.out.println("后置增强....方法的返回值:"+returnVal);
    }

    @Around(value="MyAspect.myPointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        System.out.println("环绕前增强....");
        Object obj = proceedingJoinPoint.proceed();
        System.out.println("环绕后增强....");
        return obj;
    }

    @AfterThrowing(value="MyAspect.myPointcut()",throwing="e")
    public void afterThrowing(Throwable e){
        System.out.println("不好了 出异常了!!!"+e.getMessage());
    }

    @After("MyAspect.myPointcut()")
    public void after(){
        System.out.println("最终通知...");
    }

    //定义切点
    @Pointcut("execution(* advice.UserDao.search(..))")
    private void myPointcut(){}
}
```

```html
<!-- 自动生成代理  底层就是AnnotationAwareAspectJAutoProxyCreator -->
<aop:aspectj-autoproxy />

<!-- 定义被增强的类 -->
<bean id="userDao" class="advice.UserDao"></bean>

<!-- 定义切面 -->
<bean id="myAspect" class="advice.MyAspect"></bean>
```

- 测试：
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringTest {
    @Autowired
    @Qualifier("userDao")
    IUserDao userDao;

    @Test
    public void test(){
        userDao.add();
        userDao.search();
    }
}
```