# 二、Spring IOC

## IoC 与 DI

### IoC 概念 

控制反转(Iversion of Control,IoC)是一种**设计思想**。**将原本在程序中手动创建对象的控制权，交由 Spring 框架来管理**。涉及到 2 个方面：

- **控制**

  控制对象的创建及销毁(生命周期)。

- **反转**

  将对象的控制权交给 IoC 容器。

所有的类都会在 Spring 容器中注册，告诉 Spring 你是个什么东西，你需要什么东西，然后 Spring 会在系统运行到适当的时候，把你需要的东西主动给你

**所有类的创建、销毁都由 Spring 来控制，也就是说控制对象生命周期的不是引用它的对象，而是 Spring**。对于某个具体对象而言，以前是它控制其他对象，现在所有对象都被 Spring 控制。

### DI 概念

依赖注入(Dependency Injection,DI) 。依赖注入举例：设计行李箱。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/ioc_1.png"/></div>

相应的代码如下：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/ioc_2.png"/></div>

size 固定值，改进后的代码：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/ioc_3.png"/></div>

使用  DI 方式进行改进：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/ioc_4.png"/></div>

改进后相应的代码如下：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/ioc_5.png"/></div>

不难理解，依赖注入就是**将底层类作为参数传递给上层类，实现上层对下层的控制**，**依赖注入实现控制反转**。

### IoC  和  DI 关系

IoC 是在系统运行中，**动态的向某个对象提供它所需要的其他对象**，通过 DI 来实现。

- IoC 和 DI 的关系如下图：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/ioc_6.png"/></div>

- 依赖倒置原则、IoC、DI 和  IoC 容器的关系：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/ioc_7.png"/></div>

## IoC 容器

IoC 容器功能：

- 管理 Bean 的生命周期
- 控制 Bean 依赖注入

使用  IoC 容器的好处：

- 避免在各处使用  new 来创建类，并且可以做到统一维护

- 创建实例的时候不需要了解其中的细节

  依赖注入方式获取实例：

  <div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/ioc_8.png"/></div>

  使用 IoC 容器获取获取实例（创建实例的时候不需要了解其中的细节）：

  <div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/ioc_9.png"/></div>



##  XML 配置文件解析

### BeanDefinition

BeanDefinition 是一个接口。在 Spring 中存在 3 种实现：

- RootBeanDefinition
- ChildBeanDefinition
- GenericBeanDefinition

三种实现都继承了 AbstractBeanDefinition。**AbstractBeanDefinition 是对上述的共同的类信息进行抽象**。

```java
public abstract class AbstractBeanDefinition extends 
  BeanMetadataAttributeAccessor implements BeanDefinition, Cloneable {
  //...
}
```

**BeanDefinition 是配置文件中 `<bean>`元素标签在容器中的内部表现形式**。

在配置文件中，可以定义父`<bean>` 和子`<bean>`，父`<bean>` 用 RootBeanDefinition 表示，而子`<bean>`用ChildBeanDefinition 表示，而没有父`<bean>` 的 `<bean>` 就使用 RootBeanDefinition 表示。

### BeanDefinitionRegistry

Spring 通过 BeanDefinition 将配置文件中的`<bean>`配置信息转换为容器的内部表示，并且将这些 BeanDefinition 注册 **BeanDefinitionRegistry** 中。

Spring 容器的  BeanDefinitionRegistry 就是配置信息的 内存数据库，主要是以 map 的形式保存数据，后续操作直接从 BeanDefinitionRegistry 中读取配置信息。

### Resource

Spring 采用 Resource 来对各种资源进行统一抽象。

Resource 是一个接口，定义了资源的基本操作，包括是否存在、是否可读、是否已经打开等等。

Resource 继承了 InputStreamSource。

```java
public interface Resource extends InputStreamSource {
  //...
}
```

InputStreamSource 封装任何可以返回 InputStream 的类。

```java
/*
* InputStreamSource 封装任何可以返回 InputStream 的类。
*/
public interface InputStreamSource {
  	/*
  	* 用于返回一个新的 InputStream 对象
  	*/
    InputStream getInputStream() throws IOException;
}
```

对于不同来源的资源文件都有相应的 Resource 实现：

|     资源来源     |   Resource 实现类   |
| :--------------: | :-----------------: |
|       文件       | FileSystemResource  |
|  classpath 资源  |  ClassPathResource  |
|     URL 资源     |     UrlResource     |
| InputStream 资源 | InputStreamResource |
|    Byte 数组     |  ByteArrayResource  |

### 解析过程

分为 2 大步骤：

**1、配置文件的封装**

Resource 接口**抽象了所有 Spring 内部使用到的底层资源**：File、URL、ClassPath 等。

**2、加载 Bean**

- 封装资源文件。XMLBeanDefinitionReader 对参数 Resource 使用 EncodeResource 类进行封装。
- 获取输入流。从 Resource 中获取对应的 InputStream 并构造 InputSource。
- 通过构造的  InputSource 实例和 Resource 实例继续调用函数 `doLoadBeanDefinition`。
  * 获取 XML 文件的实体解析器和验证模式(常见的验证模式有 DTD 和 XSD 两种)
  * 加载 XML 文件，并得到对应的 Document
  * 根据返回的 Document 解析并注册 BeanDefinition

## Spring  的 IoC 容器

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/ioc_11.png" width="600px"/></div>

IoC 容器其实就是一个大工厂，它用来管理我们所有的对象以及依赖关系：

- 根据 Bean 配置信息在容器内部创建 Bean 定义注册表
- 根据注册表加载，实例化 Bean，**建立 Bean 与 Bean 之间的依赖关系**
- 将 Bean 实例放入 Spring IoC 容器中，等待应用程序调用

### Spring IoC 支持的功能

- 依赖注入
- 依赖检查
- 自动装配属性
- 可指定初始化方法和销毁方法

### Spring 的 2 种 IoC 容器

- **BeanFactory**
  - IoC 容器要实现的最基础的接口
  - 采用**延迟初始化策略**(容器初始化完成后并不会创建 Bean 对象，只有当收到初始化请求时才进行初始化)
  - 由于是延迟初始化策略，因此启动速度较快，占用资源较少

- **ApplicationConext**
  - 在 BeanFactory 基础上，增加了更为高级的特性：事件发布、国际化等。
  - 在容器启动时，完成所有 Bean 的创建
  - 启动时间较长，占用资源较多

### IoC 容器的初始化过程

Spring IoC 容器的初始化过程分为 3 个阶段：Resource 定位、BeanDefinition 的载入和向 IoC 容器注册 BeanDefinition。Spring  将这 3 个阶段分离，并使用不同的模块来完成，这样可以让用户更加灵活的对这 3 个阶段进行扩展。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/01_1.png"/></div>

1、**Resource 定位**

Resource 定位指的是 BeanDefinition 的资源定位，它由 ResourceLoader 通过统一的 Resource 接口来完成，Resource 为各种形式的 BeanDefinition 的使用都提供了统一的接口。

2、**BeanDefinition 的载入**

BeanDefinition 实际上就是 POJO 对象在 IoC 容器中的抽象，通过 BeanDefiniton，IoC 容器可以方便对 POJO 对象进行管理。

3、 **向 IoC 容器注册 BeanDefinition**

向 IoC 容器注册 BeanDefinition 是通过调用 BeanDefinitionRegistry  接口来的实现来完成的，这个注册过程是把载入的 BeanDefinition 向 IoC 容器进行注册。实际上 IoC 容器内部维护这一个 HashMap，而这个注册过程其实就是将 BeanDefinition 添加至该 HashMap 中。

> 注意：BeanFactory 和 FactoryBean 的区别

BeanFactory 是  IoC 最基本的容器，负责生产和管理 Bean，为其他具体的 IoC 容器提供了最基本的规范。

FactoryBean 是一个 Bean，是一个接口，当 IoC 容器中的 Bean 实现了 FactoryBean 后，

通过 getBean(String beanName) 获取到的 Bean 对象并不是 FactoryBean 的实现类对象，而是这个实现类中的 getObject() 方法返回的对象。

要想获取 FactoryBean 的实现类对象，就是在  beanName 前面加上 "&"。

## Spring Bean 加载流程

AbstractBeanFactory 中 `getBean`最终调用 `doGetBean`方法。

```java
protected <T> T doGetBean(String name, @Nullable Class<T> requiredType, @Nullable Object[] args, boolean typeCheckOnly) throws BeansException {
  String beanName = this.transformedBeanName(name);
  Object sharedInstance = this.getSingleton(beanName);
  Object bean;
  if (sharedInstance != null && args == null) {
    if (this.logger.isTraceEnabled()) {
      if (this.isSingletonCurrentlyInCreation(beanName)) {
        this.logger.trace("Returning eagerly cached instance of singleton bean '" + beanName + "' that is not fully initialized yet - a consequence of a circular reference");
      } else {
        this.logger.trace("Returning cached instance of singleton bean '" + beanName + "'");
      }
    }

    bean = this.getObjectForBeanInstance(sharedInstance, name, beanName, (RootBeanDefinition)null);
  } else {
    if (this.isPrototypeCurrentlyInCreation(beanName)) {
      throw new BeanCurrentlyInCreationException(beanName);
    }

    BeanFactory parentBeanFactory = this.getParentBeanFactory();
    if (parentBeanFactory != null && !this.containsBeanDefinition(beanName)) {
      String nameToLookup = this.originalBeanName(name);
      if (parentBeanFactory instanceof AbstractBeanFactory) {
        return ((AbstractBeanFactory)parentBeanFactory).doGetBean(nameToLookup, requiredType, args, typeCheckOnly);
      }

      if (args != null) {
        return parentBeanFactory.getBean(nameToLookup, args);
      }

      if (requiredType != null) {
        return parentBeanFactory.getBean(nameToLookup, requiredType);
      }

      return parentBeanFactory.getBean(nameToLookup);
    }

    if (!typeCheckOnly) {
      this.markBeanAsCreated(beanName);
    }

    try {
      RootBeanDefinition mbd = this.getMergedLocalBeanDefinition(beanName);
      this.checkMergedBeanDefinition(mbd, beanName, args);
      String[] dependsOn = mbd.getDependsOn();
      String[] var11;
      if (dependsOn != null) {
        var11 = dependsOn;
        int var12 = dependsOn.length;

        for(int var13 = 0; var13 < var12; ++var13) {
          String dep = var11[var13];
          if (this.isDependent(beanName, dep)) {
            throw new BeanCreationException(mbd.getResourceDescription(), beanName, "Circular depends-on relationship between '" + beanName + "' and '" + dep + "'");
          }

          this.registerDependentBean(dep, beanName);

          try {
            this.getBean(dep);
          } catch (NoSuchBeanDefinitionException var24) {
            throw new BeanCreationException(mbd.getResourceDescription(), beanName, "'" + beanName + "' depends on missing bean '" + dep + "'", var24);
          }
        }
      }

      if (mbd.isSingleton()) {
        sharedInstance = this.getSingleton(beanName, () -> {
          try {
            return this.createBean(beanName, mbd, args);
          } catch (BeansException var5) {
            this.destroySingleton(beanName);
            throw var5;
          }
        });
        bean = this.getObjectForBeanInstance(sharedInstance, name, beanName, mbd);
      } else if (mbd.isPrototype()) {
        var11 = null;

        Object prototypeInstance;
        try {
          this.beforePrototypeCreation(beanName);
          prototypeInstance = this.createBean(beanName, mbd, args);
        } finally {
          this.afterPrototypeCreation(beanName);
        }

        bean = this.getObjectForBeanInstance(prototypeInstance, name, beanName, mbd);
      } else {
        String scopeName = mbd.getScope();
        Scope scope = (Scope)this.scopes.get(scopeName);
        if (scope == null) {
          throw new IllegalStateException("No Scope registered for scope name '" + scopeName + "'");
        }

        try {
          Object scopedInstance = scope.get(beanName, () -> {
            this.beforePrototypeCreation(beanName);

            Object var4;
            try {
              var4 = this.createBean(beanName, mbd, args);
            } finally {
              this.afterPrototypeCreation(beanName);
            }

            return var4;
          });
          bean = this.getObjectForBeanInstance(scopedInstance, name, beanName, mbd);
        } catch (IllegalStateException var23) {
          throw new BeanCreationException(beanName, "Scope '" + scopeName + "' is not active for the current thread; consider defining a scoped proxy for this bean if you intend to refer to it from a singleton", var23);
        }
      }
    } catch (BeansException var26) {
      this.cleanupAfterBeanCreationFailure(beanName);
      throw var26;
    }
  }

  if (requiredType != null && !requiredType.isInstance(bean)) {
    try {
      T convertedBean = this.getTypeConverter().convertIfNecessary(bean, requiredType);
      if (convertedBean == null) {
        throw new BeanNotOfRequiredTypeException(name, requiredType, bean.getClass());
      } else {
        return convertedBean;
      }
    } catch (TypeMismatchException var25) {
      if (this.logger.isTraceEnabled()) {
        this.logger.trace("Failed to convert bean '" + name + "' to required type '" + ClassUtils.getQualifiedName(requiredType) + "'", var25);
      }

      throw new BeanNotOfRequiredTypeException(name, requiredType, bean.getClass());
    }
  } else {
    return bean;
  }
}
```

doGetBean 大致流程如下：

**1、获取参数 name 对应的真正 beanName**

**2、尝试从缓存中加载单例**

单例在 Spring 的同一个容器中只会被创建一次，后续再获取 Bean，就直接从**单例缓存**中获取。

这里只是尝试加载，首先尝试从**缓存**中加载，如果加载不成功则再次尝试从 **singletonFactories** 中加载。

在创建依赖的时候为了避免循环依赖，在 Spring 中创建 Bean 的原则：**不等 bean 创建完成就会将创建 Bean 的 ObjectFactory 提早曝光加入到缓存中，一旦下一个 Bean 创建时需要依赖上一个 Bean 则直接使用 ObjectFactory**。

**3、Bean 的实例化**

如果从缓存中得到了 Bean 的原始状态，则需要对 Bean 进行实例化。这里有必要强调一下，缓存中记录的是原始的 Bean 状态，并一定是我们最终想要的 Bean。

**4、原型模式的依赖检查**

只有在**单例情况下**才会尝试解决循环依赖。

如果存在 A 中有 B 的属性，B 中有 A 的属性，那么当依赖注入的时 候，就会产生当 A 还未创建完的时候因为对于 B 的创建再次返回创建 A ,造成循环依赖，即              isPrototypeCurrentlyInCreation(String beanName) 判断 true。

**5、检测 parentBeanFactory**

如果缓存没有数据的话，直接转到  parentBeanFactory 去加载，条件是：

```java
parentBeanFactory != null && !this.containsBeanDefinition(beanName)
```

containsBeanDefinition 方法是检测如果当前 XML 中没有配置对应的  beanName，只能到  parentBeanfactory 中尝试一下。

**6、将 GenericBeanDefinition 对象转换为 RootBeanDefiniion 对象**

从 XML 配置文件中读取 Bean 信息会放到 GenericBeanDefinition 中， 但是所有的 Bean 后续处理都是针对 RootBeanDefinition 的。

这里做一下转换，转换的同时如果父类 Bean 不为空的话，则会进一步合并父类的属性。

**7、初始化依赖的  Bean**

Spring 在初始化某一个 Bean 的时候首先初始化这个 Bean 的依赖。

**8、依据当前 bean 的作用域对 bean 进行实例化**

Spring 中可以指定各种 scope，默认是 singleton 。

还有其他的配置比如 prototype、request 等，Spring 会根据不同的配置进行不同的初始化策略。

**9、类型转换**

将返回的 Bean 转化为 requiredType 所指定的类型。

**10、返回 Bean**

## Spring 解决循环依赖

循环依赖就是循环引用，就是两个或者多个 Bean 之间相互持有对方，比如  CircleA  引用 CircleB，CircleB 引用 CircleC，CircleC 引用 CircleA，则它们最终反映为一个环。

### 构造器循环依赖

表示通过构造器注入构成的循环依赖。此依赖是无法解决的，只能抛出 BeanConcurrentlyCreationException 异常表示循环依赖。

### setter 循环依赖

表示通过 setter 注入构成的循环依赖。 解决该依赖问题是通过 Spring 容器提前暴露刚完成构造器注入但未完成其他步骤(如  setter 注入) 的 Bean 来完成的，而且只能解决单例作用域的 Bean  的循环依赖。

对于 `scope="prototype"`的 Bean，Spring  容器无法完成依赖注入，因为 "prototype" 作用域 Bean，Spring 容器不进行缓存，因此无法提前暴露一个创建中的 Bean。对于 "singleton" 作用域 Bean，可以通过setAllowCircleReferences(false) 来禁用循环引用。 

对于单例对象来说，在 Spring 整个容器的生命周期内，有且只有一个对象，很容易想到这个对象应该存在于**缓存**中，Spring 在解决循环依赖问题的过程中就使用了"三级缓存"：

- **singletonObjects **

  用于保存 beanName 和创建 Bean 实例之间的关系，缓存的是 **Bean 实例**。

- **singletonFactories**

  用于保存 beanName 和创建 Bean 的工厂之间的关系，缓存的是为解决循环依赖而准备的 **ObjectFactory**。

- **earlySingletonObjects**

  用于保存 beanName 和创建 Bean 实例之间的关系，与 singletonObjects 的不同之处在于，当一个单例 Bean 被放到这里面后，当 Bean 还在创建过程中，就可以通过 getBean 方法获得，其目的是检查循环引用。这边缓存的也是实例，只是这边的是为解决循环依赖而提早暴露出来的实例，其实就是 ObjectFactory。

首先尝试从 singletonObjects 中获取实例，如果获取不到，再从 earlySingletonObjects 中获取，如果还获取不到，再尝试从  singletonFactories 中获取  beanName 对应的 ObjectFactory，然后调用 getObject  来创建 Bean，并放到 earlySingletonObjects 中去，并且从 singletonFactories 中 remove 掉这个 ObjectFactory。 

> 注意：singleObjects 和 earlySingletonObjects

- 两者都是以 beanName 为key，Bean 实例为 value 进行存储

- 两者得出区别在于 singletonObjects 存储的是实例化的完成的 Bean 实例，而 earlySingletonObjects 存储的是正在实例化中的 Bean，所以两个集合的内容是互斥的。


# 参考资料

- [Spring 框架小白的蜕变](https://www.imooc.com/learn/1108)

- [Spring IOC知识点一网打尽！](https://segmentfault.com/a/1190000014979704)
- [Spring核心思想，IoC与DI详解（如果还不明白，放弃java吧）](https://blog.csdn.net/Baple/article/details/53667767)
- [15个经典的Spring面试常见问题](https://mp.weixin.qq.com/s/uVoeXRLNEMK8c00u3tm9KA)