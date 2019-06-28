# Spring 中 Bean 的生命周期

Spring 中，组成应用程序的主体及由 Spring IoC 容器所管理的对象，被称之为 Bean。
简单地讲，Bean 就是由 IoC 容器初始化、装配及管理的对象，Bean 的定义以及 Bean 相互间的依赖关系通过**配置元数据**来描述。

Spring 中的 Bean 默认都是**单例**的，这些单例 Bean 在多线程程序下如何保证线程安全呢？
例如对于 Web 应用来说，Web 容器对于每个用户请求都创建一个单独的 Sevlet 线程来处理请求，
引入 Spring 框架之后，**每个 Action 都是单例的**，那么对于 Spring 托管的单例 Service Bean，如何保证其安全呢？ Spring 使用 ThreadLocal 解决线程安全问题(为每一个线程都提供了一份变量，因此可以同时访问而互不影响)。
Spring的单例是**基于BeanFactory**也就是Spring容器的，单例Bean在此容器内只有一个，
**Java的单例是基于 JVM，每个 JVM 内只有一个实例**。

## Bean 的作用域

Spring Framework 支持五种作用域:

| 类别 | 说明 |
| :--:| :--: |
| singleton | 在 SpringIoC 容器中仅存在一个 Bean 实例，Bean 以单例方式存在 |
| prototype | 每次从容器中调用 Bean 时，都返回一个新的实例 |
| request | 每次HTTP请求都会创建一个新的 Bean,该作用域仅适用于 WebApplicationContext 环境 |
| session | 同一个 Http Session 共享一个 Bean,不同 Session 使用不同 Bean,仅适用于WebApplicationContext 环境 |
| globalSession | 一般同于 Portlet 应用环境，该作用域仅适用于 WebApplicationContext 环境 |

注意：五种作用域中，request、session 和 global session 三种作用域仅在基于 web 的应用中使用(不必关心你所采用的是什么web应用框架)，只能用在基于 web 的 Spring ApplicationContext 环境。

### 1. singleton

当一个 Bean 的作用域为 singleton，那么Spring IoC容器中只会存在一个**共享的 Bean 实例**，
并且所有对 Bean 的请求，只要 id 与该 Bean 定义相匹配，则只会**返回 Bean 的同一实例**。

singleton 是单例类型(对应于单例模式)，就是**在创建容器时就同时自动创建一个Bean对象**，
不管你是否使用，但我们可以指定Bean节点的 lazy-init="true" 来延迟初始化Bean，
这时候，只有在第一次获取Bean时才会初始化Bean，即第一次请求该bean时才初始化。 每次获取到的对象都是同一个对象。
注意，singleton 作用域是Spring中的**缺省作用域**。

- 配置文件XML中将 Bean 定义成 singleton ：
```html
<bean id="ServiceImpl" class="com.southeast.service.ServiceImpl" scope="singleton">
```

- @Scope 注解的方式：

```java
@Service
@Scope("singleton")
public class ServiceImpl{

}
```

### 2. prototype

当一个Bean的作用域为 prototype，表示一个 Bean 定义对应多个对象实例。 
prototype 作用域的 Bean 会导致在每次对该 Bean 请求
(将其注入到另一个 Bean 中，或者以程序的方式调用容器的 getBean() 方法)时都会创建一个新的 bean 实例。
prototype 是原型类型，它在我们创建容器的时候并没有实例化，
而是当我们获取Bean的时候才会去创建一个对象，而且我们每次获取到的对象都不是同一个对象。

根据经验，**对有状态的 Bean 应该使用 prototype 作用域，而对无状态的 Bean 则应该使用 singleton 作用域。** 

- 配置文件XML中将 Bean 定义成 prototype ：

```html
<bean id="ServiceImpl" class="com.southeast.service.ServiceImpl" scope="prototype">
```
或者

```html
<bean id="ServiceImpl" class="com.southeast.service.ServiceImpl" singleton="false"/> 
```

- @Scope 注解的方式：

```java
@Service
@Scope("prototype")
public class ServiceImpl{

}
```

### 3. request

request只适用于**Web程序**，每一次 HTTP 请求都会产生一个新的 Bean ，
同时该 Bean 仅在当前HTTP request内有效，当请求结束后，该对象的生命周期即告结束。

在 XML 中将 bean 定义成 request ，可以这样配置：

- 配置文件XML中将 Bean 定义成 prototype ：

```html
<bean id="ServiceImpl" class="com.southeast.service.ServiceImpl" scope="request">
```


### 4. session

session只适用于**Web程序**，
session 作用域表示该针对每一次 HTTP 请求都会产生一个新的 Bean，
同时**该 Bean 仅在当前 HTTP session 内有效**。
与request作用域一样，可以根据需要放心的更改所创建实例的内部状态，
而别的 HTTP session 中根据 userPreferences 创建的实例，
将不会看到这些特定于某个 HTTP session 的状态变化。
当HTTP session最终被废弃的时候，在该HTTP session作用域内的bean也会被废弃掉。

```html
<bean id="userPreferences" class="com.foo.UserPreferences" scope="session"/>
```

### 5. globalSession

globalSession 作用域**类似于标准的 HTTP session** 作用域，
不过仅仅在基于 portlet 的 Web 应用中才有意义。
Portlet 规范定义了全局 Session 的概念，
它被所有构成某个 portlet web 应用的各种不同的 portlet所共享。
在globalSession 作用域中定义的 bean 被限定于全局portlet Session的生命周期范围内。

```html
<bean id="user" class="com.foo.Preferences "scope="globalSession"/>
```

## Bean 的生命周期

### Bean 的创建过程

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/spring_1.png"/></div>

### Bean 的销毁过程

- 若实现了 DisposableBean 接口，则会调用 destroy 方法

- 若配置了 destroy-method 属性，则会调用其配置的销毁方法

### Bean 的详细生命周期 

1、Spring 对 Bean 进行实例化。

2、Spring 将值和 Bean 的引用注入到 Bean 对应的属性中。

3、如果 Bean 实现了 BeanNameAware 接口，Spring 将 bean 的  id 传递给 setBeanName() 接口方法。

4、如果 Bean 实现了 BeanFactoryAware 接口，Spring 将调用 setBeanFactory() 接口方法，将 BeanFactory 容器实例传入。

5、如果 Bean 实现了 ApplicationContextAware 接口，Spring 将调用 setApplicationContext() 接口方法，将应用上下文的引用传入。

6、如果 Bean 实现了BeanPostProcessor 接口，Spring 将调用 postProcessBeforeInitialization() 接口方法。

7、如果 Bean 实现了InitializingBean 接口，Spring 将调用他们的 afterPropertiesSet() 接口方法，类似地，如果Bean 实现了 init-method 声明了初始化方法，该方法也会被调用。

8、如果 Bean 实现了BeanPostProcessor 接口，Spring 将调用 postProcessAfterInitialization() 接口方法。

9、此时 Bean 已经准备就绪，可以被应用程序使用了，他们将一一直驻留在应用上下文中，一直到该应用上下文被销毁。

10、如果 Bean 实现了 DisposableBean 接口，Spring 将调用它的 destroy() 接口方法。同样，如果 Bean 使用 destroy-method 声明了销毁方法，方法也会被调用。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/05_1.jpg" width=""/></div>







### 实践

#### initialize 和 destroy

有时我们需要在Bean属性值设置好之后和Bean销毁之前做一些事情，
比如检查Bean中某个属性是否被正常的设置好了。
Spring框架提供了多种方法让我们可以在Spring Bean的生命周期中执行initialization和pre-destroy方法。

> InitializingBean 和 DisposableBean 接口

- InitializingBean 接口 ：
```java
public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}
```

- DisposableBean 接口 ：
```java
public interface DisposableBean {
	void destroy() throws Exception;
}
```

实现 InitializingBean 接口的afterPropertiesSet()方法可以在**Bean属性值设置好之后做一些操作**，
实现DisposableBean接口的destroy()方法可以在**销毁Bean之前做一些操作**。

```java
public class GiraffeService implements InitializingBean,DisposableBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行InitializingBean接口的afterPropertiesSet方法");
    }
    @Override
    public void destroy() throws Exception {
        System.out.println("执行DisposableBean接口的destroy方法");
    }
}
```
这种方法比较简单，但是不建议使用。
因为这样会**将Bean的实现和Spring框架耦合在一起**。

> init-method 和 destroy-method 方法

配置文件中的配值init-method 和 destroy-method ：

```html
<bean name="giraffeService" class="com.southeast.GiraffeService" 
    init-method="initMethod" 
    destroy-method="destroyMethod">
</bean>
```

```java
public class GiraffeService {
    //通过<bean>的destroy-method属性指定的销毁方法
    public void destroyMethod() throws Exception {
        System.out.println("执行配置的destroy-method");
    }
    //通过<bean>的init-method属性指定的初始化方法
    public void initMethod() throws Exception {
        System.out.println("执行配置的init-method");
    }
}
```

需要注意的是自定义的init-method和post-method方法**可以抛异常但是不能有参数**。

这种方式比较推荐，因为可以**自己创建方法，无需将Bean的实现直接依赖于Spring的框架**。

> @PostConstruct 和 @PreDestroy注解

Spring 支持用 @PostConstruct和 @PreDestroy注解来指定 init 和 destroy 方法。
这两个注解均在javax.annotation 包中。
为了注解可以生效，
需要在配置文件中定义
org.springframework.context.annotation.CommonAnnotationBeanPostProcessor。

```html
<bean class="org.springframework.context.annotation.CommonAnnotationBeanPostProcessor"/>
```

```java
public class GiraffeService {
    @PostConstruct
    public void initPostConstruct(){
        System.out.println("执行PostConstruct注解标注的方法");
    }
    @PreDestroy
    public void preDestroy(){
        System.out.println("执行preDestroy注解标注的方法");
    }
}
```

#### XxxAware接口

有些时候我们需要在 Bean 的初始化中**使用 Spring 框架自身的一些对象**来执行一些操作，
比如获取 ServletContext 的一些参数，获取 ApplicaitionContext 中的 BeanDefinition 的名字，获取 Bean 在容器中的名字等等。
为了让 Bean 可以获取到框架自身的一些对象，Spring 提供了一组名为 XxxAware 的接口。

XxxAware接口均继承于org.springframework.beans.factory.Aware标记接口，
并提供一个将由 Bean 实现的set*方法,
Spring通过基于setter的依赖注入方式使相应的对象可以被Bean使用。

常见的 XxxAware 接口：

| 接口 | 说明 |
| :--: | :--: |
| ApplicationContextAware | 获得ApplicationContext对象,可以用来获取所有BeanDefinition的名字。 |
| BeanFactoryAware | 获得BeanFactory对象，可以用来检测Bean的作用域。 |
| BeanNameAware | 获得Bean在配置文件中定义的name。 |
| ResourceLoaderAware | 获得ResourceLoader对象，可以获得classpath中某个文件。 |
| ServletContextAware | 在一个MVC应用中可以获取ServletContext对象，可以读取context中的参数。 |
| ServletConfigAware | 在一个MVC应用中可以获取ServletConfig对象，可以读取config中的参数。 |

```java
public class GiraffeService implements   ApplicationContextAware,
        ApplicationEventPublisherAware, BeanClassLoaderAware, BeanFactoryAware,
        BeanNameAware, EnvironmentAware, ImportAware, ResourceLoaderAware{
    
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("执行setBeanClassLoader,ClassLoader Name = " + classLoader.getClass().getName());
    }
    
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("执行setBeanFactory,setBeanFactory:: giraffe bean singleton=" +  beanFactory.isSingleton("giraffeService"));
    }
    
    @Override
    public void setBeanName(String s) {
        System.out.println("执行setBeanName:: Bean Name defined in context="
                + s);
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("执行setApplicationContext:: Bean Definition Names="
                + Arrays.toString(applicationContext.getBeanDefinitionNames()));
    }
    
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        System.out.println("执行setApplicationEventPublisher");
    }
    
    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("执行setEnvironment");
    }
    
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        Resource resource = resourceLoader.getResource("classpath:spring-beans.xml");
        System.out.println("执行setResourceLoader:: Resource File Name="
                + resource.getFilename());
    }
    
    @Override
    public void setImportMetadata(AnnotationMetadata annotationMetadata) {
        System.out.println("执行setImportMetadata");
    }
}
```

#### BeanPostProcessor

上面的XxxAware接口是**针对某个实现这些接口的Bean定制初始化的过程**，
Spring同样可以针对容器中的所有Bean，或者某些Bean定制初始化过程，
只需提供一个实现BeanPostProcessor接口的类即可。 

- BeanPostProcessor 接口：
```java
public interface BeanPostProcessor{
    //postProcessBeforeInitialization方法会在容器中的Bean初始化之前执行
    public abstract Object postProcessBeforeInitialization(Object obj, String s)
        throws BeansException;

    //postProcessAfterInitialization方法在容器中的Bean初始化之后执行
    public abstract Object postProcessAfterInitialization(Object obj, String s)
        throws BeansException;
}
```

```java
public class CustomerBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) 
            throws BeansException {
        System.out.println("执行BeanPostProcessor的postProcessBeforeInitialization方法,beanName=" 
            + beanName);
        return bean;
    }
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) 
            throws BeansException {
        System.out.println("执行BeanPostProcessor的postProcessAfterInitialization方法,beanName="
            + beanName);
        return bean;
    }
}
```

将实现BeanPostProcessor接口的Bean像其他Bean一样定义在配置文件中：

```html
<bean class="com.southeast.bean.CustomerBeanPostProcessor"/>
```

## 注意

**Spring 容器可以管理 singleton 作用域下 Bean 的生命周期，在此作用域下，Spring 能够精确地知道 Bean 何时被创建，何时初始化完成，以及何时被销毁。而对于 prototype 作用域的 Bean，Spring 只负责创建，当容器创建了 Bean 的实例后，Bean 的实例就交给了客户端的代码管理，Spring 容器将不再跟踪其生命周期，并且不会管理那些被配置成 prototype 作用域的 Bean 的生命周期**。

- LifeCycleBean :

```java
public class LifeCycleBean {
    private String name;  

    public LifeCycleBean(){  
        System.out.println("LifeCycleBean()构造函数");  
    }  
    public String getName() {  
        return name;  
    }  

    public void setName(String name) {  
        System.out.println("setName()");  
        this.name = name;  
    }  

    public void init(){  
        System.out.println("this is init of lifeBean");  
    }  

    public void destroy(){  
        System.out.println("this is destory of lifeBean " + this);  
    }  
}
```

- 配置文件 :

```html
<bean id="lifeCycleBean" class="com.southeast.bean.LifeCycleBean" scope="singleton" 
     init-method="init" destroy-method="destroy" lazy-init="true"/>
```

- 测试 :

```java
public class LifeTest {
    @Test 
    public void test() {
        AbstractApplicationContext context = 
        new ClassPathXmlApplicationContext("lifeCycleBean.xml");
        LifeCycleBean life = (LifeCycleBean) context.getBean("lifeCycleBean");
        System.out.println("life:"+life);
        context.close();
    }
}
```

- 输出结果：

```html
LifeBean()构造函数
this is init of lifeBean
life:com.southeast.bean.LifeCycleBean@573f2bb1
this is destory of lifeBean com.southeast.bean.LifeCycleBean@573f2bb1
```

> **2. 非单例管理的对象**

当 scope= "prototype" 时，容器也会延迟初始化 Bean，
Spring 读取xml 文件的时候，并不会立刻创建对象，而是在第一次请求该 Bean 时才初始化(如调用getBean方法时)。

在第一次请求每一个 prototype 的 Bean 时，Spring容器都会调用其构造器创建这个对象，
然后调用init-method属性值中所指定的方法。
对象销毁的时候，Spring 容器不会帮我们调用任何方法，因为是非单例，
这个类型的对象有很多个，**Spring容器一旦把这个对象交给你之后，就不再管理这个对象了**。

- 配置文件 :

```html
<bean id="lifeCycleBeans" class="com.southeast.bean.LifeCycleBean" scope="prototype" 
     init-method="init" destroy-method="destroy" lazy-init="true"/>
```

- 测试：

```java
public class LifeTest2 {
    @Test 
    public void test() {
        AbstractApplicationContext context = 
        new ClassPathXmlApplicationContext("lifeCycleBean.xml");
        LifeCycleBean life = (LifeCycleBean) context.getBean("lifeCycleBean");
        System.out.println("life:"+life);
        
        LifeCycleBean life2 = (LifeCycleBean) context.getBean("lifeCycleBeans");
        System.out.println("life2:"+life2);
        context.close();
    }
}
```

- 输出结果：

```html
LifeBean()构造函数
this is init of lifeBean
life:com.southeast.bean.LifeCycleBean@573f2bb1
LifeBean()构造函数
this is init of lifeBean
life2:com.southeast.bean.LifeCycleBean@5ae9a829
this is destory of lifeBean LifeCycleBean@573f2bb1
```

作用域为 prototype 的 Bean ，其destroy方法并没有被调用。
如果 bean 的 scope 设为prototype时，当容器关闭时，destroy 方法不会被调用。
对于 prototype 作用域的 bean，有一点非常重要，