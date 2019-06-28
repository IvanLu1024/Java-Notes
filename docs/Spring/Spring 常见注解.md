# Spring 中常见注解

## @Contoller

SpringMVC 中，控制器 Controller 负责处理 DispatcherServlet 分发的请求，它把用户请求的数据经过业务处理层处理之后封装成一个 Model，然后再把该 Model 返回给对应的 View 进行展示。

SpringMVC 提供了一个非常简便的定义 Controller 的方法，你无需继承特定的类或者接口，只需使用 @Controller 标记一个类是 Contoller。

## @RequestMapping

使用 @RequestMapping 来映射 URL 到控制器，或者到 Controller 控制器的处理方法上。method 的值一旦指定，则处理方法只对指定的 HTTP method 类型请求处理。

可以为多个方法映射相同的 URI，不同的 HTTP method 类型，Spring MVC 根据请求的 method 类型是可以区分开这些方法的。

## @RequestParam 和 @PathVariable

在 SpringMVC 中，两者的作用都是将 request 里的参数的值绑定到 Controller 里的方法参数中，区别在于 URL 的写法不同。

- 使用 @RequestParam 时，URL 是这样的：

```html
http://host:port/path?参数名=参数值
```

- 使用 @PathVariable 时，URL 是这样的：

```html
http://host:port/path/参数值
```

## @Autowired

@Autowired 可以对成员变量、成员方法和构造函数进行标注，来完成自动装配工作。

## @Service、 @Contrller、 @Repository 和 @Component

 @Service、 @Contrller、 @Repository 其实这 3 个注解和 @Component 是等效的，用在实现类上：

- @Service 用于标注业务层组件
- @Controller 用于标注控制层组件
- @Repository 用于编著数据访问组件
- @Component 泛指组件，当组件不好归类时，可以使用这个注解进行标注

## @Value

在 Spring 3.0 中，可以通过使用 @Value，对一些如 xxx.properties 文件中的文件，进行键值对的注入。

## @ResponseBody

该注解用于将 Controller 中方法返回的对象，通过适当的 HttpMessageConverter 转换为指定的格式后，写入到 Response 对象的 body s数据区。 