# Spring 中涉及到的设计模式

## [工厂模式](https://duhouan.github.io/Java-Notes/#/./OO/01创建型?id=_2-简单工厂（simple-factory）)

BeanFactory 用来创建各种不同的 Bean。

## [单例模式](https://duhouan.github.io/Java-Notes/#/./OO/01创建型?id=_1-单例（singleton）)

在  Spring 配置文件中定义的  Bean 默认为单利模式。

## [模板方法模式](https://duhouan.github.io/Java-Notes/#/./OO/02行为型?id=_10-模板方法（template-method）)

用来解决代码重复的问题。比如 JdbcTempolate。

## [代理模式](https://duhouan.github.io/Java-Notes/#/./OO/03结构型?id=_7-代理（proxy）)

AOP、事务都大量运用了代理模式。

## [原型模式](https://duhouan.github.io/Java-Notes/#/./OO/01创建型?id=_6-原型模式（prototype）)

特点在于通过"复制"一个已经存在的实例来返回新的实例，而不是新建实例。被复制的实例就是我们所称的"原型"，这个原型是可定制的。

## [责任链模式](https://duhouan.github.io/Java-Notes/#/./OO/02行为型?id=_1-责任链（chain-of-responsibility）)

在 SpringMVC 中，会经常使用一些拦截器(HandlerInterceptor)，当存在多个拦截器的时候，所有的拦截器就构成了一条拦截器链。

## [观察者模式](https://duhouan.github.io/Java-Notes/#/./OO/02行为型?id=_7-观察者（observer）)

Spring 中提供了一种监听机制，即 ApplicationListenber，可以实现 Spring 容器内的事件监听。