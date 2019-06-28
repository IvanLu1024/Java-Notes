# 一、Spring 概述

## Spring 框架
Spring 是**分层**的 JavaSE/EE **一站式** 轻量级开源框架。

- **分层**

  SUN 提供的 EE 的三层结构：Web 层、业务层、数据访问层。

- **一站式**

  Spring 框架对三层结构的每层都有解决方案:

  * Web 层：[SpringMVC](https://duhouan.github.io/Java-Notes/#/./Spring/06SpringMVC)
  * 业务层：SpringBean 管理
  * 业务层：JDBCTemplate

Spring 框架的几个模块：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/spring_1.gif"/></div>
组成 Spring 框架的每个模块（或组件）都可以单独存在，或者与其他一个或多个模块联合实现。每个模块的功能如下：

- **核心容器（Spring Core）**：核心容器提供 Spring 框架的基本功能。核心容器的主要组件是 `BeanFactory`，它是工厂模式的实现。`BeanFactory` 使用*控制反转* （IOC） 模式将应用程序的配置和依赖性规范与实际的应用程序代码分开。
- **Spring 上下文**：Spring 上下文是一个配置文件，向 Spring 框架提供上下文信息。Spring 上下文包括企业服务，例如 JNDI、EJB、电子邮件、国际化、校验和调度功能。
- **Spring AOP**：通过配置管理特性，Spring AOP 模块直接将面向方面的编程功能集成到了 Spring 框架中。所以，可以很容易地使 Spring 框架管理的任何对象支持 AOP。Spring AOP 模块为基于 Spring 的应用程序中的对象提供了事务管理服务。通过使用 Spring AOP，不用依赖 EJB 组件，就可以将声明性事务管理集成到应用程序中。
- **Spring DAO**：JDBC DAO 抽象层提供了有意义的异常层次结构，可用该结构来管理异常处理和不同数据库供应商抛出的错误消息。异常层次结构简化了错误处理，并且极大地降低了需要编写的异常代码数量（例如打开和关闭连接）。Spring DAO 的面向 JDBC 的异常遵从通用的 DAO 异常层次结构。
- **Spring ORM**：Spring 框架插入了若干个 ORM 框架，从而提供了 ORM 的对象关系工具，其中包括 JDO、Hibernate 和 iBatis SQL Map。所有这些都遵从 Spring 的通用事务和 DAO 异常层次结构。
- **Spring Web 模块**：Web 上下文模块建立在应用程序上下文模块之上，为基于 Web 的应用程序提供了上下文。所以，Spring 框架支持与 Jakarta Struts 的集成。Web 模块还简化了处理多部分请求以及将请求参数绑定到域对象的工作。
- **Spring MVC 框架**：MVC 框架是一个全功能的构建 Web 应用程序的 MVC 实现。通过策略接口，MVC 框架变成为高度可配置的，MVC 容纳了大量视图技术，其中包括 JSP、Velocity、Tiles、iText 和 POI。

Spring 框架优点：

- **方便解耦，简化开发** 

  Spring 就是一个大工厂，可以将所有对象创建和依赖关系维护，交给 Spring 管理

- **AOP 编程的支持**

   Spring 提供面向切面编程，可以方便的实现对程序进行权限拦截、运行监控等功能

- **声明式事务的支持**

  只需要通过配置就可以完成对事务的管理，而无需手动编程

- **方便程序的测试**

  Spring 对 Junit4 支持，可以通过注解方便的测试 Spring 程序

- **方便集成各种优秀框架**

  Spring不排斥各种优秀的开源框架，其内部提供了对各种优秀框架（如：Struts、Hibernate、MyBatis、Quartz等）的直接支持

- **降低 JavaEE API 的使用难度**

  Spring 对 JavaEE 开发中非常难用的一些 API（JDBC、JavaMail、远程调用等），都提供了封装，使这些API应用难度大大降低

## Spring 核心

- **IOC**

  控制反转(Inversion of Control,IoC)：将对象的创建，交由 Spring 完成。 

- **AOP**

  面向切面变成(Aspect Oriented Programming,AOP):是面向对象思想的延伸，不是替换面向对象思想，是用来解决使用面向对象中出现的一些问题的。

## Spring 入门程序
- 创建 Spring 的配置文件

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

- 在配置文件中配置类

```html
<bean id="userService" class="code_01_test.service.UserServiceImpl"></bean>
```

- 测试

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
