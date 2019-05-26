<!-- GFM-TOC -->

<!-- GFM-TOC -->

# 简介

Spring 的事务有两种类型：

- 全局事务（分布式事务）
- 局部事务（单机事务）

我们平时常用的是单机事务，也就是操作一个数据库的事务。

按照使用方式来分，又可以分为编程式事务模型（TransactionTemplate）和声明式事务模型（@Transactional注解），后者可以理解为AOP + 编程式事务模型。

# 一、编程式事务模型

## 1.1 统一事务模型

在统一事务模型中，最重要的一个类就是TransactionTemplate，其中最重要的方法是execute方法，其源码如下：

```java
@Override
	public <T> T execute(TransactionCallback<T> action) throws TransactionException {
		if (this.transactionManager instanceof CallbackPreferringPlatformTransactionManager) {
			return ((CallbackPreferringPlatformTransactionManager) this.transactionManager).execute(this, action);
		}
		else {
            //获取当前事务的状态
			TransactionStatus status = this.transactionManager.getTransaction(this);
			T result;
			try {
				result = action.doInTransaction(status);
			}
			catch (RuntimeException ex) {
				// 出现异常的回滚操作 
				rollbackOnException(status, ex);
				throw ex;
			}
			catch (Error err) {
				// 出现Error的回滚操作
				rollbackOnException(status, err);
				throw err;
			}
			catch (Throwable ex) {
				// Transactional code threw unexpected exception -> rollback
				rollbackOnException(status, ex);
				throw new UndeclaredThrowableException(ex, "TransactionCallback threw undeclared checked exception");
			}
			this.transactionManager.commit(status);
			return result;
		}
	}
```

其中transactionManager是PlatformTransactionManager接口，负责事务的创建、提交、回滚策略。这里TransactionTemplate相当于实现了[模板方法](<https://github.com/DuHouAn/Java/blob/master/Object_Oriented/notes/02%E8%A1%8C%E4%B8%BA%E5%9E%8B.md#10-%E6%A8%A1%E6%9D%BF%E6%96%B9%E6%B3%95template-method>)+[策略模式](<https://github.com/DuHouAn/Java/blob/master/Object_Oriented/notes/02%E8%A1%8C%E4%B8%BA%E5%9E%8B.md#9-%E7%AD%96%E7%95%A5strategy>)这两种设计模式。

针对不同的厂商，只需要提供不同的PlatformTransactionManager实现即可。我们在使用的时候，只需要通过Spring IOC，告诉Spring，要注入哪个TransactionManager，要使用哪种策略即可。

> Spring 如何保证所有数据库操作都是使用同一个数据库连接呢？
>
> [补充资料](<https://zhuanlan.zhihu.com/p/38772486>)

## 1.2 事务传播级别

事务传播行为：**用来描述由某一个事务传播行为修饰的方法被嵌套进另一个方法的事务时如何传播**。当事务方法被另一个事务方法调用时，必须指定事务应该如何传播。

Spring中有七种事务传播类型：

| 类型                                     | 说明                                                         |
| :--------------------------------------- | :----------------------------------------------------------- |
| PROPAGATION_REQUIRED（required）         | 表示**当前方法必须运行在事务中**。如果当前事务存在，方法将会在该事务中运行。否则，会启动一个新的事务。 |
| PROPAGATION_SUPPORTS（support）          | 表示支持当前事务，如果当前没有事务，就以无事务方式执行。     |
| PROPAGATION_MANDATORY（mandatory）       | 表示使用当前的事务，如果当前没有事务，就抛出异常。           |
| PROPAGATION_REQUIRES_NEW（required_new） | 表示新建事务，如果当前存在事务，把当前事务挂起。             |
| PROPAGATION_NOT_SUPPORTED（not_support） | 表示以无事务方式执行操作，如果当前存在事务，就把当前事务挂起。 |
| PROPAGATION_NEVER（never）               | 表示以无事务方式执行，如果当前存在事务，则抛出异常。         |
| PROPAGATION_NESTED（nested）             | 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION_REQUIRED类似的操作。 |



## 1.3 事务隔离级别

事务隔离级别是指多个事务之间的隔离程度，其中TransactionDefinition 接口中定义了五个表示隔离级别的常量：

| 隔离级别                               | 说明                                                         |
| -------------------------------------- | ------------------------------------------------------------ |
| ISOLATION_DEFAULT（默认）              | 这是默认值，表示使用底层数据库的默认隔离级别。               |
| ISOLATION_READ_UNCOMMITTED（读未提交） | 该隔离级别表示一个事务可以读取另一个事务修改但还没有提交的数据，该级别不能防止脏读和不可重复读，因此很少使用该隔离级别 |
| ISOLATION_READ_COMMITTED（读可提交）   | 该隔离级别表示一个事务只能读取另一个事务已经提交的数据。该级别可以防止脏读，这也是大多数情况下的推荐值 |
| ISOLATION_REPEATABLE_READ（可重复读）  | 该隔离级别表示一个事务在整个过程中可以多次重复执行某个查询，并且每次返回的记录都相同。即使在多次查询之间有新增的数据满足该查询，这些新增的记录也会被忽略。该级别可以防止脏读和不可重复读 |
| ISOLATION_SERIALIZABLE（可串行化）     | 所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。但是，这将严重影响程序的性能，通常情况下也不会用到该级别 |

> 这里和数据库中的隔离级别的概念是相通的，可参考[Java-Notes](<https://github.com/DuHouAn/Java-Notes/blob/master/DataBase/03%E9%9A%94%E7%A6%BB%E7%BA%A7%E5%88%AB.md>)



# 二、声明式事务模型

常使用的 @Transactional 注解方式来使用事务，比编程式方式方便得多。

主要是利用了[AOP原理](<https://github.com/DuHouAn/Java-Notes/blob/master/Spring/02SpringAOP.md>)来实现了这个功能，对于@Transactional 修饰的方法，Spring会为其创建一个代理对象，这个代理对象中就会有事务开启、提交、回滚等逻辑。

Spring中，事务对应的Advisor是BeanFactoryTransactionAttributeSourceAdvisor，它的adivce是TransactionInterceptor，pointcut是TransactionAttributeSourcePointcut，其作用是判断方法是否被@Transactional 注解修饰。



# 三、编程式和声明式的对比

| 类型           | 优点                 | 缺点                                           |
| -------------- | -------------------- | ---------------------------------------------- |
| 编程式事务模型 | 显式调用，不易出错   | 侵入式代码，编码量大                           |
| 声明式事务模型 | 简洁，对代码侵入性小 | 隐藏了实现细节，出现问题不易定位，容易导致误用 |



# 四、补充资料

- [使用方式](https://juejin.im/post/5b010f27518825426539ba38)

- [使用@Transactional注解可能会遇到的问题](<https://zhuanlan.zhihu.com/p/38208248>)

# 参考资料

<https://zhuanlan.zhihu.com/p/38772486>

<https://zhuanlan.zhihu.com/p/41864893>

<https://segmentfault.com/a/1190000013341344>

