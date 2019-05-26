<!-- GFM-TOC -->
* [SpringIOC](#SpringIOC)
    * [IOC装配Bean](#IOC装配Bean)
    * [IOC使用注解方式装配Bean](#IOC使用注解方式装配Bean)
    * [SpringIOC原理](#SpringIOC原理)
    * [SpringIOC源码分析](#SpringIOC源码分析)
<!-- GFM-TOC -->

# SpringIOC

## IOC装配Bean
### Spring框架Bean实例化的方式
提供了三种方式实例化Bean:

- 构造方法实例化(默认无参数)
- 静态工厂实例化
- 实例工厂实例化

#### 1.无参数构造方法的实例化

```html
<!-- 默认情况下使用的就是无参数的构造方法 -->
 <bean id="bean1" class="service2.Bean1"></bean>
```

#### 2.静态工厂实例化
```html
<!-- 第二种使用静态工厂实例化 -->
<bean id="bean2" class="service2.Bean2Factory" factory-method="getBean2"></bean>
```

#### 3.实例工厂实例化
```html
<!-- 第三种使用实例工厂实例化 -->
<bean id="bean3" factory-bean="bean3Factory" factory-method="getBean3"></bean>
<bean id="bean3Factory" class="service2.Bean3Factory"/>
```

```java
/**
 * 使用无参数的构造方法实例化
 */
public class Bean1 {
    public Bean1(){

    }
}
```

```java
/**
 * 使用静态工厂的方式实例化
 *
 */
public class Bean2 {

}
```

```java
/**
 * Bean2的静态工厂
 */
public class Bean2Factory {
	public static Bean2 getBean2(){
		System.out.println("静态工厂的获得Bean2的方法...");
		return new Bean2();
	}
}
```

```java
/**
 * 使用实例工厂实例化
 *
 */
public class Bean3 {

}
```

```java
/**
 * 实例工厂
 */
public class Bean3Factory {
	public Bean3 getBean3(){
		System.out.println("Bean3实例工厂的getBean3方法...");
		return new Bean3();
	}
}
```
- 测试三种实例化Bean的方式：
````java
public class SpringTest {
    @Test
    // 无参数的构造方法的实例化
    public void demo1() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "applicationContext2.xml");
        Bean1 bean1 = (Bean1) applicationContext.getBean("bean1");
        System.out.println(bean1);
    }

    @Test
    // 静态工厂实例化
    public void demo2() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "applicationContext2.xml");
        Bean2 bean2 = (Bean2) applicationContext.getBean("bean2");
        System.out.println(bean2);
    }

    @Test
    // 实例工厂实例化
    public void demo3() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "applicationContext2.xml");
        Bean3 bean3 = (Bean3) applicationContext.getBean("bean3");
        System.out.println(bean3);
    }
}
````

### Bean的其他配置
#### 1.id和name的区别

id遵守XML约束的id的约束。id约束保证这个**属性的值是唯一的**,而且必须以字母开始，
可以使用字母、数字、连字符、下划线、句话、冒号。name没有这些要。

#### 2.类的作用范围
scope属性 :

| 属性 | 解释 |
| :--: | :--: |
| singleton | 单例的(默认的值) |
| prototype | 多例的 |
| request |	web开发中.创建了一个对象,将这个对象存入request范围,request.setAttribute() |
| session | web开发中.创建了一个对象,将这个对象存入session范围,session.setAttribute() |
| globalSession	| 一般用于Porlet应用环境。指的是分布式开发。不是porlet环境,globalSession等同于session |

注意：实际开发中主要使用**singleton**,**prototype**

#### 3.Bean的生命周期:
配置初始化和销毁的方法:
```html
init-method=”setup”
destroy-method=”teardown”
```
执行销毁的时候,必须手动关闭工厂,而且只对scope=”singleton”有效.

Bean的生命周期的11个步骤:
- 1.instantiate bean对象实例化
- 2.populate properties 封装属性
- 3.如果Bean实现BeanNameAware 执行 setBeanName
- 4.如果Bean实现BeanFactoryAware 或者 ApplicationContextAware 设置工厂 setBeanFactory 或者上下文对象 setApplicationContext
- 5.如果存在类实现 BeanPostProcessor（后处理Bean） ，执行postProcessBeforeInitialization
- 6.如果Bean实现InitializingBean 执行 afterPropertiesSet 
- 7.调用<bean init-method="init"> 指定初始化方法 init
- 8.如果存在类实现 BeanPostProcessor（处理Bean） ，执行postProcessAfterInitialization
- 9.执行业务处理
- 10.如果Bean实现 DisposableBean 执行 destroy
- 11.调用<bean destroy-method="customerDestroy"> 指定销毁方法 customerDestroy


### Bean中属性注入
Spring支持两种属性注入方法：

- 构造方法注入
- setter方法注入

#### 1.构造器注入
```html
 <!--构造器注入-->
<bean id="car" class="service3.Car">
    <!-- <constructor-arg name="name" value="宝马"/>
    <constructor-arg name="price" value="1000000"/> -->
    <constructor-arg index="0" type="java.lang.String" value="奔驰"/>
    <constructor-arg index="1" type="java.lang.Double" value="2000000"/>
</bean>
```

#### 2.setter方法注入
```html
<!--setter方法注入-->
<bean id="car2" class="service3.Car2">
    <!-- <property>标签中name就是属性名称,value是普通属性的值,ref:引用其他的对象 -->
    <property name="name" value="保时捷"/>
    <property name="price" value="5000000"/>
</bean>
```

```html
<!--方法注入对象属性-->
<bean id="person" class="service3.Person">
    <property name="name" value="李四"/>
    <property name="car2" ref="car2"/>
</bean>
```

```java
/**
 * 构造方法注入
 */
public class Car {
	private String name;
	private Double price;
	
	public Car() {
		super();
	}

	public Car(String name, Double price) {
		super();
		this.name = name;
		this.price = price;
	}

	@Override
	public String toString() {
		return "Car [name=" + name + ", price=" + price + "]";
	}
}
```

```java
/**
 * setter方法注入
 */
public class Car2 {
	private String name;
	private Double price;
	
	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Car2 [name=" + name + ", price=" + price + "]";
	}
}
```

```java
public class Person {
	private String name;
	private Car2 car2;
	public void setName(String name) {
		this.name = name;
	}
	public void setCar2(Car2 car2) {
		this.car2 = car2;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", car2=" + car2 + "]";
	}
}
```
- 测试属性注入：
```java
public class SpringTest {
    @Test
    public void demo1(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "applicationContext3.xml");
        Car car = (Car) applicationContext.getBean("car");
        System.out.println(car);
    }

    @Test
    public void demo2(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "applicationContext3.xml");
        Car2 car2 = (Car2) applicationContext.getBean("car2");
        System.out.println(car2);
    }

    @Test
    public void demo3(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "applicationContext3.xml");
        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person);
    }
}
```

### 集合属性的注入
```html
<!--集合属性的注入-->
<bean id="collectionBean" class="service4.CollectionBean">
    <!-- 注入List集合 -->
    <property name="list">
        <list>
            <value>童童</value>
            <value>小凤</value>
        </list>
    </property>

    <!-- 注入set集合 -->
    <property name="set">
        <set>
            <value>杜宏</value>
            <value>如花</value>
        </set>
    </property>

    <!-- 注入map集合 -->
    <property name="map">
        <map>
            <entry key="刚刚" value="111"/>
            <entry key="娇娇" value="333"/>
        </map>
    </property>

    <property name="properties">
        <props>
            <prop key="username">root</prop>
            <prop key="password">123</prop>
        </props>
    </property>
</bean>
```

```java
public class CollectionBean {
	private List<String> list;
	private Set<String> set;
	private Map<String,Integer> map;
	private Properties properties;
	
	public void setSet(Set<String> set) {
		this.set = set;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return "CollectionBean [list=" + list + ", set=" + set + ", map=" + map
				+ ", properties=" + properties + "]";
	}
}
```
- 测试集合属性的注入
```java
public class SpringTest {
    @Test
    public void demo1(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "applicationContext4.xml");
        CollectionBean collectionBean = (CollectionBean) applicationContext.getBean("collectionBean");
        System.out.println(collectionBean);
    }
}
```

## IOC使用注解方式装配Bean
### Spring的注解装配Bean
Spring2.5 引入使用注解去定义Bean

- @Component：描述Spring框架中Bean 

Spring的框架中提供了与@Component注解等效的三个注解:

- @Repository 用于对DAO实现类进行标注
- @Service 用于对Service实现类进行标注
- @Controller 用于对Controller实现类进行标注

**普通属性**;
```java
@Value(value="itcast")
private String info;
```

**对象属性**:@Autowired--自动装配默认使用类型注入

```java
@Autowired
@Qualifier("userDao")
//按名称进行注入.
```

```java
@Autowired
@Qualifier("userDao")		
private UserDao userDao;
```
等价于
```java
@Resource(name="userDao")
private UserDao userDao;
```

配置Bean初始化方法和销毁方法:
```java
@PostConstruct 初始化
@PreDestroy  销毁
```
```java
@Repository("userDao")
public class UserDao {
	
}
```

```java
/**
 * 注解的方式装配Bean
 */
// 在Spring配置文件中<bean id="userService" class="cn.itcast.demo1.UserService">
// @Component("userService")
@Service(value="userService")
@Scope
public class UserService {
	@Value(value="itcast")
	private String info;
	
    @Autowired(required=true)
    @Qualifier("userDao")
	private UserDao userDao;

    //等价于
	//@Resource(name="userDao")
	//private UserDao userDao;
	
	public void sayHello(){
		System.out.println("Hello Spring Annotation..."+info);
	}

	@PostConstruct
	public void setup(){
		System.out.println("初始化...");
	}
	
	@PreDestroy
	public void teardown(){
		System.out.println("销毁...");
	}
}
```

```java
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
```

## SpringIOC原理
### IoC与DI
Ioc是Spring的核心，贯穿始终。

所谓IoC，对于Spring框架来说，就是由Spring来负责控制对象的生命周期和对象间的关系。

传统的程序开发中，在一个对象中，如果要使用另外的对象，就必须得到它（自己new一个，或者从JNDI中查询一个），
使用完之后还要将对象销毁（比如Connection等），对象始终会和其他的接口或类**耦合**起来。

所有的类都会在Spring容器中登记，告诉Spring你是个什么，你需要什么，
然后Spring会在系统运行到适当的时候，把你要的东西主动给你，同时也把你交给其他需要你的东西。
**所有的类的创建、销毁都由 spring来控制**，也就是说控制对象生存周期的不再是引用它的对象，而是Spring。
对于某个具体的对象而言，以前是它控制其他对象，现在是所有对象都被spring控制，所以这叫控制反转。

IoC的一个重点是在系统运行中，**动态的向某个对象提供它所需要的其他对象**。
这一点是通过DI(Dependency Injection，依赖注入)来实现的。
比如对象A需要操作数据库，以前我们总是要在A中自己编写代码来获得一个Connection对象，
有了Spring我们就只需要告诉Spring，A中需要一个Connection，
至于这个Connection怎么构造，何时构造，A不需要知道。
在系统运行时，Spring会在适当的时候制造一个Connection，然后像打针一样，
注射到A当中，这样就完成了对各个对象之间关系的控制。
A需要依赖 Connection才能正常运行，而这个Connection是由Spring注入到A中的，依赖注入的名字就这么来的。

<div align="center">
    <img src="https://gitee.com/IvanLu1024/picts/raw/3d251b08c118fec8fbd15c1f87d9fadc9883d895/blog/spring/20190523211034.png"/>
</div>



那么DI是如何实现的呢？ Java 1.3之后一个重要特征是反射(reflection)，
它允许程序在运行的时候动态的生成对象、执行对象的方法、改变对象的属性，
**Spring就是通过[反射](<https://github.com/DuHouAn/Java/blob/master/JavaBasics/notes/反射.md>)来实现注入的**。

## IOC容器的优势

- 避免在各处使用new来创建类，这个容器可以自动对代码进行初始化，并且可以做到统一维护。
- 创建实例的时候不需要了解其中的细节，这个容器相当于一个工厂。

<div align="center">
    <img src="https://gitee.com/IvanLu1024/picts/raw/56221a755d760942bfbdd855c53cf5aefaf86d88/blog/spring/20190523212117.png"/>
</div>



## BeanFactory与ApplicationContext的比较

- BeanFactory 是 Spring 框架的基础设施，面向Spring
- ApplicationContext 面向使用 Spring 框架的开发者

> 补充参考资料：<https://www.jianshu.com/p/17b66e6390fd>

## SpringIOC源码分析

### 初始化
Spring IOC的初始化过程，整个脉络很庞大，初始化的过程主要就是**读取XML资源**，并**解析**，
最终**注册到Bean Factory中**。

<div align="center"><img src="pics\\01_1.png"/></div>

### 注入依赖
当完成初始化IOC容器后，如果Bean没有设置lazy-init(延迟加载)属性，那么Bean的实例就会在初始化IOC完成之后，及时地进行**初始化**。
初始化时会**创建实例**，然后根据配置利用反射对实例进行进一步操作，具体流程如下所示：

<div align="center"><img src="pics\\01_2.png"/></div>

### getBean方法的代码逻辑

- 转换beanName
- 从缓存中加载实例
- 实例化Bean
- 检测partentBeanFactory
- 初始化依赖的Bean
- 创建Bean

### [Spring核心源码学习](https://yikun.github.io/2015/05/29/Spring-IOC%E6%A0%B8%E5%BF%83%E6%BA%90%E7%A0%81%E5%AD%A6%E4%B9%A0/)