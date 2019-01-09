<!-- GFM-TOC -->
* [Spring概述](#Spring概述)
    * [Spring框架](#Spring框架)
    * [Spring的核心](#Spring的核心)
    * [Spring优点](#Spring优点)
    * [Spring入门程序](#Spring入门程序)
<!-- GFM-TOC -->
# Spring概述

## Spring框架
Spring是**分层**的JavaSE/EE full-stack(**一站式**) 轻量级开源框架

> 分层:

SUN提供的EE的三层结构:web层、业务层、数据访问层（持久层，集成层）

> 一站式:

Spring框架有对三层的每层解决方案:

- web层:Spring MVC
- 持久层:JDBC Template 
- 业务层:Spring的Bean管理


## Spring的核心
> IOC

控制反转(Inverse of Control):将对象的创建权,交由Spring完成。

> AOP

面向切面编程(Aspect Oriented Programming):**面向对象的功能延伸**。不是替换面向对象,是用来解决OO中一些问题.


## Spring优点
- **方便解耦，简化开发**； Spring就是一个大工厂，可以将所有对象创建和依赖关系维护，交给Spring管理

- **AOP编程的支持**； Spring提供面向切面编程，可以方便的实现对程序进行权限拦截、运行监控等功能

- **声明式事务的支持**：只需要通过配置就可以完成对事务的管理，而无需手动编程

- **方便程序的测试**：Spring对Junit4支持，可以通过注解方便的测试Spring程序

- **方便集成各种优秀框架**：Spring不排斥各种优秀的开源框架，其内部提供了对各种优秀框架
（如：Struts、Hibernate、MyBatis、Quartz等）的直接支持

- **降低JavaEE API的使用难度**：Spring 对JavaEE开发中非常难用的一些API（JDBC、JavaMail、远程调用等），
都提供了封装，使这些API应用难度大大降低

## Spring入门程序
### 1.创建Spring的配置文件
在src下创建一个applicationContext.xml。

引入XML的约束: 找到xsd-config.html.引入beans约束:

```html
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:p="http://www.springframework.org/schema/p"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
</beans>
```

### 2.在配置文件中配置类

```html
<bean id="userService" class="code_01_test.service.UserServiceImpl"></bean>
```

### 3.测试
```java
public interface UserService {
    void say();
}
```

```java
public class UserServiceImpl implements UserService{
    @Override
    public void say() {
        System.out.println("say");
    }
}
```

```java
public class SpringTest {
    /**
     * 传统方式
     */
    @Test
    public void test(){
        UserService userService=new UserServiceImpl();
        userService.say();
    }


    /**
     * 创建一个工厂类.使用工厂类来创造对象
     */
    @Test
    public void test2(){
        // 创建一个工厂类
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService=(UserService)applicationContext.getBean("userService");
        userService.say();
    }

    /**
     *加载磁盘路径下的配置文件
     */
    @Test
    public void test3() {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
                "applicationContext.xml");
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.say();
    }

    @Test
    public void test4(){
        // ClassPathResource  FileSystemResource
        BeanFactory beanFactory = new XmlBeanFactory(new FileSystemResource("applicationContext.xml"));
        UserService userService = (UserService)  beanFactory.getBean("userService");
        userService.say();
    }
}
```

### IOC(控制反转)和DI(依赖注入)区别
IOC(控制反转)：对象的创建权,由Spring管理。

DI(依赖注入)：在Spring创建对象的过程中,把**对象依赖的属性**注入到类中。

<div align="center"><img src="pics\\6666.jpg" width="600"/></div>