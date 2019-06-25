# SpringMVC

## MVC(Model-View-Controller)

MVC 的原理图：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/06_1.jpg"/></div>

- Model(模型端)

**Model 封装的是数据源和所有基于对这些数据的操作**。

在一个组件中，Model 往往表示组件的状态和操作这些状态的方法，往往是一系列的公开方法。 通过这些公开方法，便可以取得 Model 的所有功能。

在这些公开方法中，有些是**取值方法**，让系统其他部分可以得到模型端的内部状态参数， 其他的**改值方法**则允许外部修改模型端的内部状态。 模型端还必须有方法**登记视图**，以便在模型端的内部状态发生变化时，可以通知视图端。

- View(视图端)

**View 封装的是对数据源 Model 的一种显示**。
一个模型可以有多个视图，并且可以在需要的时候动态地登记上所需的视图。而一个视图理论上也可以和不同的模型关联起来。

- Controller(控制器端)

**封装的是外界作用于 Model 的操作。**通常，这些操作会转发到模型上，并调用模型中相应的一个或者多个方法(这个方法就是前面在介绍模型的时候说的改值方法)。
一般 Controller 在 Model 和 View 之间起到了沟通的作用，处理用户在 View 上的输入，并转发给 Model 来更改Model 状态值。
这样 Model 和 View 两者之间可以做到**松散耦合**，甚至可以彼此不知道对方，而由Controller连接起这两个部分。也在前言里提到，MVC 用到了策略模式，这是**因为 View 用一个特定的 Controller 的实例来实现一个特定的响应策略**，更换不同的Controller，可以改变 View 对用户输入的响应。

## MVC 中涉及到的设计模式

利用**"观察者"**让控制器 (Controller) 和视图 (View) 可以随最新的状态改变而改变。

视图 (View) 和控制器 (Controller) 则实现了**"策略模式"**，控制器是视图的行为。 

### [观察者模式](https://duhouan.github.io/Java-Notes/#/./OO/02行为型?id=_7-观察者（observer）)

### [策略模式](https://duhouan.github.io/Java-Notes/#/./OO/02行为型?id=_9-策略（strategy）)


## SpringMVC 框架

SpringMVC 框架是以请求为驱动，围绕 Servlet 设计，
将请求发给控制器，然后通过模型对象，分派器来展示请求结果视图。
其中核心类是 DispatcherServlet，它是一个 Servlet，顶层实现Servlet接口。

### 1. 使用

需要在 web.xml 中配置 DispatcherServlet 。
并且需要配置 Spring 监听器ContextLoaderListener

- 配置监听器：
```html
<listener>
	<listener-class>org.springframework.web.context.ContextLoaderListener
	</listener-class>
</listener>
```

- 配置 DispatcherServlet：
```html
<servlet>
	<servlet-name>springmvc</servlet-name>
	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	<!-- 如果不设置init-param标签，
	则必须在/WEB-INF/下创建xxx-servlet.xml文件，其中xxx是servlet-name中配置的名称。 -->
	<init-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:spring/springmvc-servlet.xml</param-value>
	</init-param>
	<load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
	<servlet-name>springmvc</servlet-name>
	<url-pattern>/</url-pattern>
</servlet-mapping>
```

### 2. 原理

原理图如下：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/06_2.jpg"/></div>

**流程说明**：

1. 客户端(浏览器)发送请求，直接请求到 DispatcherServlet。

2. DispatcherServlet 根据请求信息调用 HandlerMapping，解析请求对应的 Handler。

3. 解析到对应的 Handler(也就是我们平常说的 Controller 控制器)后，
开始由 HandlerAdapter 适配器处理。

4. HandlerAdapter 会根据 Handler 来调用真正的处理器开处理请求，
并处理相应的业务逻辑。

5. 处理器处理完业务后，会返回一个 ModelAndView 对象，
Model 是返回的数据对象，View 是个**逻辑上的 View**。

6. ViewResolver 会根据逻辑 View 查找实际的 View。

7. DispaterServlet 把返回的 Model 传给 View(视图渲染)。

8. 把 View 返回给请求者(浏览器)

### 3. 重要组件

> DispatcherServlet

前端控制器,不需要工程师开发,由框架提供。

作用：**Spring MVC 的入口函数**。**接收请求，响应结果，相当于转发器，中央处理器**。
有了 DispatcherServlet 减少了其它组件之间的耦合度。
DispatcherServlet是整个流程控制的中心，由它调用其它组件处理用户的请求，
DispatcherServlet的存在**降低了组件之间的耦合性**。

> HandlerMapping

处理器映射器,不需要工程师开发,由框架提供。

作用：根据请求的url查找Handler。
**HandlerMapping负责根据用户请求找到Handler即处理器(Controller)**，
SpringMVC提供了不同的映射器实现不同的映射方式，
例如：配置文件方式，实现接口方式，注解方式等。

> HandlerAdapter

处理器适配器

作用：按照特定规则(HandlerAdapter要求的规则)去执行Handler,
通过HandlerAdapter对处理器进行执行，这是适配器模式的应用，
通过扩展适配器可以对更多类型的处理器进行执行。

[适配器模式](https://github.com/DuHouAn/Java/blob/master/Object_Oriented/notes/03%E7%BB%93%E6%9E%84%E5%9E%8B.md#1-%E9%80%82%E9%85%8D%E5%99%A8adapter)

> Handler

处理器,需要工程师开发

注意：编写Handler时按照HandlerAdapter的要求去做，
这样适配器才可以去正确执行Handler,
Handler是继DispatcherServlet前端控制器的**后端控制器**，
在DispatcherServlet的控制下Handler对具体的用户请求进行处理。
由于Handler涉及到具体的用户业务请求，
所以一般情况需要工程师根据业务需求开发Handler。

> ViewResolver

视图解析器,不需要工程师开发,由框架提供

作用：进行视图解析，根据逻辑视图名解析成真正的视图(view)。
ViewResolver负责将处理结果生成View视图，
ViewResolver首先根据逻辑视图名解析成物理视图名即具体的页面地址，
再生成View视图对象，最后对View进行渲染将处理结果通过页面展示给用户。
springmvc框架提供了很多的View视图类型，
包括：jstlView、freemarkerView、pdfView等。
一般情况下需要通过页面标签或页面模版技术将模型数据通过页面展示给用户，
需要由工程师根据业务需求开发具体的页面。

> View

视图View,需要工程师开发。

View是一个接口，实现类支持不同的View类型(jsp、freemarker、pdf...)

### 4. DispatcherServlet 源码解析

```java
package org.springframework.web.servlet;
 
@SuppressWarnings("serial")
public class DispatcherServlet extends FrameworkServlet {
	public static final String MULTIPART_RESOLVER_BEAN_NAME = "multipartResolver";
	public static final String LOCALE_RESOLVER_BEAN_NAME = "localeResolver";
	public static final String THEME_RESOLVER_BEAN_NAME = "themeResolver";
	public static final String HANDLER_MAPPING_BEAN_NAME = "handlerMapping";
	public static final String HANDLER_ADAPTER_BEAN_NAME = "handlerAdapter";
	public static final String HANDLER_EXCEPTION_RESOLVER_BEAN_NAME = "handlerExceptionResolver";
	public static final String REQUEST_TO_VIEW_NAME_TRANSLATOR_BEAN_NAME = "viewNameTranslator";
	public static final String VIEW_RESOLVER_BEAN_NAME = "viewResolver";
	public static final String FLASH_MAP_MANAGER_BEAN_NAME = "flashMapManager";
	public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = 
	    DispatcherServlet.class.getName() + ".CONTEXT";
	public static final String LOCALE_RESOLVER_ATTRIBUTE = 
	    DispatcherServlet.class.getName() + ".LOCALE_RESOLVER";
	public static final String THEME_RESOLVER_ATTRIBUTE = 
	    DispatcherServlet.class.getName() + ".THEME_RESOLVER";
	public static final String THEME_SOURCE_ATTRIBUTE = 
	    DispatcherServlet.class.getName() + ".THEME_SOURCE";
	public static final String INPUT_FLASH_MAP_ATTRIBUTE = 
	    DispatcherServlet.class.getName() + ".INPUT_FLASH_MAP";
	public static final String OUTPUT_FLASH_MAP_ATTRIBUTE = 
	    DispatcherServlet.class.getName() + ".OUTPUT_FLASH_MAP";
	public static final String FLASH_MAP_MANAGER_ATTRIBUTE = 
	    DispatcherServlet.class.getName() + ".FLASH_MAP_MANAGER";
	public static final String EXCEPTION_ATTRIBUTE =
	    DispatcherServlet.class.getName() + ".EXCEPTION";
	public static final String PAGE_NOT_FOUND_LOG_CATEGORY = 
	    "org.springframework.web.servlet.PageNotFound";
	private static final String DEFAULT_STRATEGIES_PATH = 
	    "DispatcherServlet.properties";
	protected static final Log pageNotFoundLogger = 
	    LogFactory.getLog(PAGE_NOT_FOUND_LOG_CATEGORY);
	private static final Properties defaultStrategies;
	static {
		try {
			ClassPathResource resource = 
			    new ClassPathResource(DEFAULT_STRATEGIES_PATH, DispatcherServlet.class);
			defaultStrategies = PropertiesLoaderUtils.loadProperties(resource);
		}
		catch (IOException ex) {
			throw new IllegalStateException("Could not load 'DispatcherServlet.properties': " + ex.getMessage());
		}
	}
 
	/** Detect all HandlerMappings or just expect "handlerMapping" bean? */
	private boolean detectAllHandlerMappings = true;
 
	/** Detect all HandlerAdapters or just expect "handlerAdapter" bean? */
	private boolean detectAllHandlerAdapters = true;
 
	/** Detect all HandlerExceptionResolvers or just expect "handlerExceptionResolver" bean? */
	private boolean detectAllHandlerExceptionResolvers = true;
 
	/** Detect all ViewResolvers or just expect "viewResolver" bean? */
	private boolean detectAllViewResolvers = true;
 
	/** Throw a NoHandlerFoundException if no Handler was found to process this request? **/
	private boolean throwExceptionIfNoHandlerFound = false;
 
	/** Perform cleanup of request attributes after include request? */
	private boolean cleanupAfterInclude = true;
 
	/** MultipartResolver used by this servlet */
	private MultipartResolver multipartResolver;
 
	/** LocaleResolver used by this servlet */
	private LocaleResolver localeResolver;
 
	/** ThemeResolver used by this servlet */
	private ThemeResolver themeResolver;
 
	/** List of HandlerMappings used by this servlet */
	private List<HandlerMapping> handlerMappings;
 
	/** List of HandlerAdapters used by this servlet */
	private List<HandlerAdapter> handlerAdapters;
 
	/** List of HandlerExceptionResolvers used by this servlet */
	private List<HandlerExceptionResolver> handlerExceptionResolvers;
 
	/** RequestToViewNameTranslator used by this servlet */
	private RequestToViewNameTranslator viewNameTranslator;
 
	private FlashMapManager flashMapManager;
 
	/** List of ViewResolvers used by this servlet */
	private List<ViewResolver> viewResolvers;
 
	public DispatcherServlet() {
		super();
	}
 
	public DispatcherServlet(WebApplicationContext webApplicationContext) {
		super(webApplicationContext);
	}
	@Override
	protected void onRefresh(ApplicationContext context) {
		initStrategies(context);
	}
 
	protected void initStrategies(ApplicationContext context) {
		initMultipartResolver(context);
		initLocaleResolver(context);
		initThemeResolver(context);
		initHandlerMappings(context);
		initHandlerAdapters(context);
		initHandlerExceptionResolvers(context);
		initRequestToViewNameTranslator(context);
		initViewResolvers(context);
		initFlashMapManager(context);
	}
}
```

DispatcherServlet类中与属性相关的Bean:

| Bean | 说明 |
| :---: | :--: |
| HandlerMapping | 用于Handlers映射请求和一系列的对于拦截器的前处理和后处理，大部分用@Controller注解。 |
| HandlerAdapter | 帮助DispatcherServlet处理映射请求处理程序的适配器，而不用考虑实际调用的是哪个处理程序。|
| ViewResolver | 根据实际配置解析实际的View类型 |
| ThemeResolver | 解决Web应用程序可以使用的主题，例如提供个性化布局。|
| MultipartResolver | 解析多部分请求，以支持从HTML表单上传文件。 |
| FlashMapManager | 存储并检索可用于将一个请求属性传递到另一个请求的input和output的FlashMap，通常用于重定向。|

在Web MVC框架中，每个DispatcherServlet都拥自己的 WebApplicationContext，
它继承了ApplicationContext。
WebApplicationContext 包含了其上下文和Servlet实例之间共享的所有的基础框架的Bean。

> HandlerMapping

HandlerMapping 接口的实现类：
- SimpleUrlHandlerMapping 类通过**配置文件**把URL映射到Controller类。
- DefaultAnnotationHandlerMapping 类通过**注解**把URL映射到Controller类。

> HandlerAdapter

HandlerAdapter 接口的实现类：
- AnnotationMethodHandlerAdapter 类通过注解，把请求URL映射到Controller类的方法上。

> HandlerExceptionResolver

HandlerExceptionResolver 接口的实现类：
- SimpleMappingExceptionResolver 类通过配置文件进行异常处理。
- AnnotationMethodHandlerExceptionResolver 类通过注解进行异常处理。

> ViewResolver

ViewResolver接口实现类：

- UrlBasedViewResolver 类通过配置文件，把一个视图名交给到一个View来处理。