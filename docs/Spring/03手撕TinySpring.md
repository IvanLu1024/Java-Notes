<!-- GFM-TOC -->
* [手撕TinySpring](#手撕TinySpring)
    * [TinySpring-版本1](#TinySpring-版本1)
    * [TinySpring-版本2](#TinySpring-版本2)
<!-- GFM-TOC -->

# 手撕TinySpring

实现了一个简单的TinySpring，并实现了如下功能：

- 根据 xml 配置文件加载相关 Bean
- 对 BeanPostProcessor 类型的 Bean 提供支持
- 对 BeanFactoryAware 类型的 Bean 提供支持
- 实现基于 JDK 动态代理的 AOP
- 整合 IOC 和 AOP，使得二者可很好的协同工作

## TinySpring-版本1
### 简单的 IOC 实现
简单的 IOC 容器只需4步即可实现，如下：

1. 加载 xml 配置文件，遍历其中的标签

2. 获取标签中的 id 和 class 属性，加载 class 属性对应的类，并创建 Bean

3. 遍历标签中的标签，获取属性值，并将属性值填充到 Bean 中

4. 将 Bean 注册到 Bean 容器中

#### 1. 容器实现类
```java
/**
 * 容器实现类，完成上述4步
 */
public class BeanFactory {
    //存储注册的Bean
    private Map<String, Object> beanMap = new HashMap<>();

    public BeanFactory(String location) throws Exception{
        location=this.getClass().getClassLoader().getResource(location).getFile();
        loadBeans(location);
    }

    public Object getBean(String name) {
        Object bean = beanMap.get(name);
        if (bean == null) {
            throw new IllegalArgumentException("There is no bean with such name " + name);
        }
        return bean;
    }

    private void loadBeans(String location) throws Exception{
        // 1. 加载 xml 配置文件，遍历其中的标签
        // 加载 xml 配置文件
        InputStream inputStream = new FileInputStream(location);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputStream);
        //root标签是"<beans>"
        Element root = doc.getDocumentElement();
        NodeList nodes = root.getChildNodes();

        // 遍历 <bean> 标签
        //将在xml文件中申明的所有Bean都注册到容器中
        //2. 获取标签中的 id 和 class 属性，加载 class 属性对应的类，并创建 Bean
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                String id = ele.getAttribute("id");
                String className = ele.getAttribute("class");

                //根据遍历得到的bean标签，创建对象
                // 加载 beanClass
                Class clazz = null;
                try {
                    clazz = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return;
                }

                // 创建 Beans 实例
                Object bean = clazz.newInstance();

                // 3. 遍历标签中的标签，获取属性值，并将属性值填充到 Bean 中
                // 遍历 <property> 标签
                NodeList propertyNodes = ele.getElementsByTagName("property");
                for (int j = 0; j < propertyNodes.getLength(); j++) {
                    Node propertyNode = propertyNodes.item(j);
                    if (propertyNode instanceof Element) {
                        Element propertyElement = (Element) propertyNode;
                        String name = propertyElement.getAttribute("name");
                        String value = propertyElement.getAttribute("value");

                        // 利用反射获取Bean的属性
                        Field attribute = bean.getClass().getDeclaredField(name);
                        //将 Bean 相关字段访问权限设为可访问
                        attribute.setAccessible(true);

                        if (value != null && value.length() > 0) {
                            //处理 <property name="height" value="1949mm"/> 这样的情况
                            //将Bean的属性值value填充到attribute字段中
                            attribute.set(bean, value);
                        } else {
                            //处理 <property name="wheel" ref="wheel"/> 这样的情况
                            String ref = propertyElement.getAttribute("ref");
                            if (ref == null || ref.length() == 0) {
                                throw new IllegalArgumentException("ref config error");
                            }
                            //将Bean的属性值value填充到attribute字段中
                            attribute.set(bean, getBean(ref));
                        }
                        
                        // 4. 将 Bean 注册到 Bean 容器中
                        // 将 bean 注册到 Bean 容器中
                        registerBean(id, bean);
                    }
                }
            }
        }
    }

    //将Bean注册到容器中
    private void registerBean(String id, Object bean) {
        beanMap.put(id, bean);
    }
}
```

#### 2. Bean
```java
public class Car {
    private String name;
    private String length;
    private String width;
    private String height;
    private Wheel wheel;

    //...

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", length='" + length + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", wheel=" + wheel +
                '}';
    }
}
```

```java
public class Wheel {
    private String brand;
    private String price ;

    //...

    @Override
    public String toString() {
        return "Wheel{" +
                "brand='" + brand + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
```

#### 3. xml文件中的配置
```html
<?xml version="1.0" encoding="UTF-8"?>
<beans>
    <bean id="wheel" class="com.southeast.simpleIOC.bean.Wheel">
        <property name="brand" value="Michelin" />
        <property name="price" value="26500" />
    </bean>

    <bean id="car" class="com.southeast.simpleIOC.bean.Car">
        <property name="name" value="Mercedes Benz G 500"/>
        <property name="length" value="4717mm"/>
        <property name="width" value="1855mm"/>
        <property name="height" value="1949mm"/>
        <property name="wheel" ref="wheel"/>
    </bean>
</beans>
```

- 测试：
```java
/**
 * 测试简单IOC
 */
public class BeanFactoryTest {
    @Test
    public void test() throws Exception {
        BeanFactory beanFactory=new BeanFactory("applicationContext.xml");
        Car car=(Car)beanFactory.getBean("car");
        System.out.println(car);
        Wheel wheel=(Wheel)beanFactory.getBean("wheel");
        System.out.println(wheel);
   }
}
```

- 输出结果：
```html
Car{name='Mercedes Benz G 500', length='4717mm', width='1855mm', height='1949mm', wheel=Wheel{brand='Michelin', price='26500'}}
Wheel{brand='Michelin', price='26500'}
```

### 简单的 AOP 实现
这里 AOP 是**基于 JDK 动态代理实现**的，只需3步即可完成：

1. 定义一个包含切面逻辑的对象，这里假设叫 logMethodInvocation。

2. 定义一个 Advice 对象（实现了 InvocationHandler 接口），
并将上面的 logMethodInvocation 和 目标对象传入。

3. 将上面的 Adivce 对象和目标对象传给 JDK 动态代理方法，为目标对象生成代理。

### 1. 通知
```java
//1. 定义一个包含切面逻辑的对象，这里假设叫 logMethodInvocation。
/**
* 实现对象中包含切面逻辑
*/
public interface MethodInvocation {
    void invoke();
}
```

```java
//2. 定义一个 Advice 对象（实现了 InvocationHandler 接口），
//并将上面的 logMethodInvocation 和 目标对象传入。
/**
 * 所谓通知(增强)是指拦截到Joinpoint之后所要做的事情就是通知。
 */
public interface Advice extends InvocationHandler {
}
```
```java
import java.lang.reflect.Method;

public class BeforeAdvice implements Advice{
    private Object bean;
    private MethodInvocation methodInvocation;

    public BeforeAdvice(Object bean,MethodInvocation methodInvocation){
        this.bean = bean;
        this.methodInvocation = methodInvocation;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        methodInvocation.invoke();
        //在方法代码执行前进行了增强
        Object obj=method.invoke(bean,args);
        return obj;
    }
}
```

### 2. 被代理的接口和类
```java
public interface IUserService {
    void sayHello();
}
```

```java
public class UserService implements IUserService{

    @Override
    public void sayHello() {
        System.out.println("Hello");
    }
}
```

### 3. 代理实现类
```java
public class BeanFactory {
    public static Object getProxy(Object bean, Advice advice) {
        return Proxy.newProxyInstance(
                bean.getClass().getClassLoader(),
                bean.getClass().getInterfaces(),
                advice);
    }
}
```

- 测试：
```java
public class BeanFactoryTest {
    @Test
    public void test(){
        // 1. 创建一个 MethodInvocation 实现类
        //MethodInvocation logTask = () -> System.out.println("log task start");
        MethodInvocation logTask = new MethodInvocation() {
            @Override
            public void invoke() {
                System.out.println("log task start");
            }
        };

        // 2. 创建一个 Advice
        UserService userService=new UserService();
        Advice beforeAdvice = new BeforeAdvice(userService, logTask);

        // 3. 为目标对象生成代理 -->这里使用JDK动态代理-->对接口进行代理
        IUserService proxy = (IUserService) BeanFactory.getProxy(userService,beforeAdvice);

        proxy.sayHello();
    }
}
```

- 输出结果：
```html
log task start
Hello
```

到目前为止，IOC和AOP只是独立的模块。

### [TinySpring-版本1 相关代码](https://github.com/DuHouAn/Java-Notes/tree/master/Spring/TinySpring)

## TinySpring-版本2

### 实现 IOC
#### 1. BeanFactory 的生命流程

- 1. BeanFactory 加载 Bean 配置文件，将读到的 Bean 配置封装成 BeanDefinition 对象
- 2. 将封装好的 BeanDefinition 对象注册到 BeanDefinition 容器中
- 3. 注册 BeanPostProcessor 相关实现类到 BeanPostProcessor 容器中
- 4. BeanFactory 进入就绪状态
- 5. 外部调用 BeanFactory 的 getBean(String name) 方法，BeanFactory 着手实例化相应的 Bean
- 6. 重复步骤 3 和 4，直至程序退出，BeanFactory 被销毁

上面简单罗列了 BeanFactory 的生命流程，也就是 IOC 容器的生命流程。

#### 2. 实现IOC所用到的辅助类
> BeanDefinition

BeanDefinition，从字面意思上翻译成中文就是 “Bean 的定义”。
从翻译结果中就可以猜出这个类的用途，即根据 Bean 配置信息生成相应的 Bean 详情对象。
举个例子，**如果把 Bean 比作是电脑，那么 BeanDefinition 就是这台电脑的配置清单**。
我们从外观上无法看出这台电脑里面都有哪些配置，也看不出电脑的性能咋样。
但是通过配置清单，我们就可了解这台电脑的详细配置。
我们可以知道这台电脑是不是用了牙膏厂的 CPU，BOOM 厂的固态硬盘等。
透过配置清单，我们也就可大致评估出这台电脑的性能。

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/03_1.png" width="400"/></div>

具体实现中，BeanDefinition 和 xml 有如下对应：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/03_2.png" width="600"/></div>

```java
/**
 * Bean的内容及元数据，保存在BeanFactory中，
 * 包装Bean的实体
 */
public class BeanDefinition {
    private Object bean;

    private Class beanClass;

    private String beanClassName;

    private PropertyValues propertyValues = new PropertyValues();

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }
}
```

> BeanReference

上图中可以看出，BeanReference 对象**保存的是 bean 配置中 ref 属性对应的值**，
在后续 BeanFactory 实例化 Bean 时，会根据 BeanReference 保存的值去实例化 Bean 所依赖的其他 Bean。

```java
public class BeanReference {
    private String name;
    private Object bean;

    public BeanReference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
```

> PropertyValue

PropertyValue 中有两个字段 name 和 value，用于**记录 bean 配置中的标签的属性值**。

```java
public class PropertyValue {
    //name和value都是常量。与bean配置中的标签属性对应
    private final String name;
    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
```

> PropertyValues

PropertyValue 的复数形式，在功能上等同于 List。
那么为什么 Spring 不直接使用 List，而自己定义一个新类呢？
答案是要获得一定的控制权，这样就可以封装一些操作，
代码和注释如下：

```java
/**
 * 包装一个对象所有的PropertyValue
 * 为什么封装而不是直接用List?因为可以封装一些操作。
 */
public class PropertyValues {
	private final List<PropertyValue> propertyValueList = new ArrayList<PropertyValue>();

	public PropertyValues() {
	}

	public void addPropertyValue(PropertyValue pv) {
        //TODO:这里可以对于重复propertyName进行判断，直接用list没法做到
		this.propertyValueList.add(pv);
	}

	public List<PropertyValue> getPropertyValues() {
		return this.propertyValueList;
	}
}
```

#### 3. 解析配置文件

BeanDefinitionReader 的实现类 XmlBeanDefinitionReader负责加载和解析配置文件。

XmlBeanDefinitionReader 做了如下几件事情：

1. 将 xml 配置文件加载到内存中

2. 获取根标签下所有的标签

3. 遍历获取到的标签列表，并从标签中读取 id，class 属性

4. 创建 BeanDefinition 对象，并将刚刚读取到的 id，class 属性值保存到对象中

5. 遍历标签下的标签，从中读取属性值，并保持在 BeanDefinition 对象中

6. 将 <id, BeanDefinition> 键值对缓存在 Map 中，留作后用

7. 重复3、4、5、6步，直至解析结束

- BeanDefinitionReader 接口：

```java
public interface BeanDefinitionReader {
    void loadBeanDefinitions(String location) throws Exception;
}
```
- XmlBeanDefinitionReader 实现类：

```java
public class XmlBeanDefinitionReader implements BeanDefinitionReader {
    private Map<String,BeanDefinition> registry;

    public XmlBeanDefinitionReader(){
        this.registry = new HashMap<>();
    }

    //1. 将 xml 配置文件加载到内存中
    @Override
    public void loadBeanDefinitions(String location) throws Exception {
        InputStream inputStream = new FileInputStream(location);
        doLoadBeanDefinitions(inputStream);
    }

    //将Stream转化成 BeanDefinition
    private void doLoadBeanDefinitions(InputStream inputStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputStream);
        registerBeanDefinitions(doc);
        inputStream.close();
    }

    public void registerBeanDefinitions(Document doc) {
        Element root = doc.getDocumentElement();

        parseBeanDefinitions(root);
    }

    //2. 获取根标签下所有的标签
    private void parseBeanDefinitions(Element root) {
        NodeList nl = root.getChildNodes();
        //3. 遍历获取到的标签列表，并从标签中读取 id，class 属性
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                processBeanDefinition(ele);
            }
        }
    }

    //标签中读取 id，class 属性
    private void processBeanDefinition(Element ele) {
        String name = ele.getAttribute("id");
        String className = ele.getAttribute("class");
        //4. 创建 BeanDefinition 对象，并将刚刚读取到的 id，class 属性值保存到对象中
        BeanDefinition beanDefinition = new BeanDefinition();

        //5. 遍历标签下的标签，从中读取属性值，并保持在 BeanDefinition 对象中
        processProperty(ele, beanDefinition);
        beanDefinition.setBeanClassName(className);
        //6. 将 <id, BeanDefinition> 键值对缓存在 Map 中，留作后用
        getRegistry().put(name, beanDefinition);
    }

    private void processProperty(Element ele, BeanDefinition beanDefinition) {
        NodeList propertyNode = ele.getElementsByTagName("property");
        for (int i = 0; i < propertyNode.getLength(); i++) {
            Node node = propertyNode.item(i);
            if (node instanceof Element) {
                Element propertyEle = (Element) node;
                String name = propertyEle.getAttribute("name");
                String value = propertyEle.getAttribute("value");
                if (value != null && value.length() > 0) {
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
                } else {
                    String ref = propertyEle.getAttribute("ref");
                    if (ref == null || ref.length() == 0) {
                        throw new IllegalArgumentException("Configuration problem: <property> element for property '"
                                + name + "' must specify a ref or value");
                    }
                    BeanReference beanReference = new BeanReference(ref);
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
                }
            }
        }
    }


    public Map<String, BeanDefinition> getRegistry() {
        return registry;
    }
}
```

#### 4. 注册 BeanPostProcessor
BeanPostProcessor 接口是 **Spring 对外拓展的接口**之一，
其主要用途提供一个机会，让开发人员能够控制 Bean 的实例化过程。
**通过实现这个接口，我们就可在 Bean 实例化时，对 Bean 进行一些处理**。
比如，我们所熟悉的 AOP 就是在这里将切面逻辑织入相关 Bean 中的。
正是因为有了 BeanPostProcessor 接口作为桥梁，才使得 AOP 可以和 IOC 容器产生联系。

```java
/**
 * BeanPostProcessor 接口是Spring 对外拓展的接口之一，
 * 其主要用途提供一个机会，让开发人员能够控制 bean 的实例化过程。
 **通过实现这个接口，我们就可在 Bean 实例化时，对 Bean 进行一些处理。
 */
public interface BeanPostProcessor {
    Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception;
    Object postProcessAfterInitialization(Object bean, String beanName) throws Exception;
}
```

BeanFactory 是怎么注册 BeanPostProcessor 相关实现类的？

XmlBeanDefinitionReader 在完成解析工作后，
BeanFactory 会将它解析得到的 <id, BeanDefinition> 键值对注册到自己的 beanDefinitionMap 中。
BeanFactory 注册好 BeanDefinition 后，就立即开始注册 BeanPostProcessor 相关实现类。
这个过程比较简单：

1. 根据 BeanDefinition 记录的信息，寻找所有实现了 BeanPostProcessor 接口的类。

2. 实例化 BeanPostProcessor 接口的实现类

3. 将实例化好的对象放入 List中

4. 重复2、3步，直至所有的实现类完成注册


```java
/**
 * Bean的容器
 *
 * XmlBeanDefinitionReader 在完成解析工作后，
 * BeanFactory 会将它解析得到的 <id, BeanDefinition>
 * 键值对注册到自己的 beanDefinitionMap 中。
 * BeanFactory 注册好 BeanDefinition 后，就立即开始注册 BeanPostProcessor 相关实现类。
 */
public interface BeanFactory {
    Object getBean(String name) throws Exception;
}
```

```java
public class XmlBeanFactory implements BeanFactory{
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private List<String> beanDefinitionNames = new ArrayList<>();

    // 用来存储注册的BeanPostProcessor
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    private XmlBeanDefinitionReader beanDefinitionReader;

    //初始化的时候，自动装配属性
    public XmlBeanFactory(String location) throws Exception {
        beanDefinitionReader = new XmlBeanDefinitionReader();
        loadBeanDefinitions(location);
    }

    private void loadBeanDefinitions(String location) throws Exception {
        beanDefinitionReader.loadBeanDefinitions(location);
        registerBeanDefinition();
        registerBeanPostProcessor();
    }

    /**
     * 注册BeanDefinition
     */
    private void registerBeanDefinition() {
        //获取从xml文件中读取到的信息
        Map<String, BeanDefinition> map=beanDefinitionReader.getRegistry();
        for(String name:map.keySet()){
            BeanDefinition beanDefinition=map.get(name);
            beanDefinitionMap.put(name,beanDefinition);
            beanDefinitionNames.add(name);
        }
    }

    /**
     * 注册BeanPostProcessor
     */
    public void registerBeanPostProcessor() throws Exception {
        List beans = getBeansForType(BeanPostProcessor.class);
        for (Object bean : beans) {
            addBeanPostProcessor((BeanPostProcessor) bean);
        }
    }

    /**
     *  注册BeanPostProcessor
     *  1. 根据 BeanDefinition 记录的信息，寻找所有实现了 BeanPostProcessor 接口的类。
     *  2. 实例化 BeanPostProcessor 接口的实现类
     *  3. 将实例化好的对象放入 List中
     */
    private List getBeansForType(Class type) throws Exception {
        List beans = new ArrayList<>();
        for (String beanDefinitionName : beanDefinitionNames) {
            if (type.isAssignableFrom(beanDefinitionMap.get(beanDefinitionName).getBeanClass())) {
                beans.add(getBean(beanDefinitionName));
            }
        }
        return beans;
    }

    //将beanPostProcessor放入容器中
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.add(beanPostProcessor);
    }
    
    @Override
    public Object getBean(String name) throws Exception {
        //...
    }
}
```

#### 5. BeanFactory中 getBean 过程解析

在完成了 xml 的解析、BeanDefinition 的注册以及 BeanPostProcessor 的注册过程后。
BeanFactory 初始化的工作算是结束了，此时 BeanFactory 处于就绪状态，等待外部程序的调用。

外部程序一般都是通过调用 BeanFactory 的 getBean(String name) 方法来获取容器中的 Bean。
BeanFactory 具有**延迟实例化 Bean 的特性**，也就是等外部程序需要的时候，才实例化相关的 Bean。
这样做的好处是比较显而易见的，
**第一是提高了 BeanFactory 的初始化速度**，
**第二是节省了内存资源**。

Spring中Bean的实例化过程：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/03_3.png" width="600"/></div>

我们这里进行了简化：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/03_4.png" width="200"/></div>

简化后的实例化流程如下：

1. 实例化 Bean 对象

2. 将配置文件中配置的属性注入到新创建的 Bean 对象中

3. 检查 Bean 对象是否实现了 Aware 一类的接口，如果实现了则把相应的依赖设置到 Bean 对象中。

4. 调用 BeanPostProcessor 前置处理方法，
即 postProcessBeforeInitialization(Object bean, String beanName)

5. 调用 BeanPostProcessor 后置处理方法，
即 postProcessAfterInitialization(Object bean, String beanName)

6. Bean 对象处于就绪状态，可以使用了

```java
/**
 * getBean 简化方案
 */
@Override
public Object getBean(String name) throws Exception {
    BeanDefinition beanDefinition = beanDefinitionMap.get(name);
    if (beanDefinition == null) {
        throw new IllegalArgumentException("no this bean with name " + name);
    }

    //根据beanDefiniton获取Bean
    Object bean = beanDefinition.getBean();
    if (bean == null) {
        //1. 实例化 Bean 对象
        //2.将配置文件中配置的属性注入到新创建的 Bean 对象中
        //3 .检查 Bean 对象是否实现了 Aware 一类的接口，如果实现了则把相应的依赖设置到 Bean 对象中。
        bean = createBean(beanDefinition);

        //4. 调用 BeanPostProcessor 前置处理方法，
        // 即 postProcessBeforeInitialization(Object bean, String beanName)
        //5. 调用 BeanPostProcessor 后置处理方法，
        // 即 postProcessAfterInitialization(Object bean, String beanName)
        bean = initializeBean(bean, name);
        beanDefinition.setBean(bean);
    }
    return bean;
}

private Object createBean(BeanDefinition bd) throws Exception {
    //1. 实例化 Bean 对象
    Object bean = bd.getBeanClass().newInstance();
    // 2. 将配置文件中配置的属性注入到新创建的 Bean 对象中
    applyPropertyValues(bean, bd);

    return bean;
}

private void applyPropertyValues(Object bean, BeanDefinition bd) throws Exception {
    //3 .检查 Bean 对象是否实现了 Aware 一类的接口，如果实现了则把相应的依赖设置到 Bean 对象中。
    if (bean instanceof BeanFactoryAware) {
        ((BeanFactoryAware) bean).setBeanFactory(this);
    }
    PropertyValues propertyValues=bd.getPropertyValues();
    for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
        Object value = propertyValue.getValue();
        if (value instanceof BeanReference) {
            BeanReference beanReference = (BeanReference) value;
            value = getBean(beanReference.getName());
        }

        //保证了使用 set方法为对象注入属性
        try {
            Method declaredMethod = bean.getClass().getDeclaredMethod(
                    "set" + propertyValue.getName().substring(0, 1).toUpperCase()
                            + propertyValue.getName().substring(1), value.getClass());
            declaredMethod.setAccessible(true);
            //执行 setter方法
            declaredMethod.invoke(bean, value);
        } catch (NoSuchMethodException e) {
            Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
            declaredField.setAccessible(true);
            //创建 setter
            declaredField.set(bean, value);
        }
    }
}

private Object initializeBean(Object bean, String name) throws Exception {
    //4. 调用 BeanPostProcessor 前置处理方法，
    // 即 postProcessBeforeInitialization(Object bean, String beanName)
    for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
        bean = beanPostProcessor.postProcessBeforeInitialization(bean, name);
    }

    //5. 调用 BeanPostProcessor 后置处理方法，
    // 即 postProcessAfterInitialization(Object bean, String beanName)
    for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
        bean = beanPostProcessor.postProcessAfterInitialization(bean, name);
    }
    return bean;
}
```

### 实现 AOP

AOP 是基于**动态代理模式**实现的，具体实现上可以基于 JDK 动态代理或者Cglib 动态代理（代理类实现）。
其中 JDK 动态代理只能代理实现了接口的对象，而 Cglib 动态代理则无此限制。
所以在为没有实现接口的对象生成代理时，只能使用 Cglib。
在本项目中，暂时只实现了基于 JDK 动态代理的代理对象生成器。

1. AOP 逻辑介入 BeanFactory 实例化 Bean 的过程

2. 根据 Pointcut 定义的匹配规则，判断当前正在实例化的 Bean 是否符合规则

3. 如果符合，代理生成器将通知 Advice 织入 Bean 相关方法中，并为目标 Bean 生成代理对象

4. 将生成的 Bean 的代理对象返回给 BeanFactory 容器，到此，AOP 逻辑执行结束


####  1. AOP的术语

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

#### 2. 基于 JDK 动态代理的 AOP 实现
- 辅助类及接口

> TargetSource : 封装被代理的对象的类的相关信息
```java
public class TargetSource {
    private Class<?> targetClass;

    private Class<?>[] interfaces;

    private Object target;

    public TargetSource(IUserDao userDao, Object target, Class<?> targetClass, Class<?>... interfaces) {
        this.target = target;
        this.targetClass = targetClass;
        this.interfaces = interfaces;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Object getTarget() {
        return target;
    }

    public Class<?>[] getInterfaces() {
        return interfaces;
    }
}
```

> MethodMatcher : 方法匹配器,用于判断对哪些方法进行拦截

```java
public interface MethodMatcher {
  /**
   * 匹配该方法是否是要拦截的方法
   */
  boolean matches(Method method, Class targetClass);
}
```

> AdvisedSupport : AdvisedSupport封装了TargetSource、MethodInterceptor、MethodMatcher

```java
/**
 * AdvisedSupport封装了
 * TargetSource 要代理的目标对象
 * MethodInterceptor 方法拦截器
 * MethodMatcher  方法匹配器
 */
public class AdvisedSupport {
    // 要拦截的对象
    private TargetSource targetSource;

     //方法拦截器
     //Spring的AOP只支持方法级别的调用，所以其实在AopProxy里，我们只需要将MethodInterceptor放入对象的方法调用
    private MethodInterceptor methodInterceptor;

    // 方法匹配器，判断是否是需要拦截的方法
    private MethodMatcher methodMatcher;

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
```

> ReflectiveMethodInvocation : 反射型拦截器，在方法调用的时候对方法进行拦截

```java
public class ReflectiveMethodInvocation implements MethodInvocation {
    protected Object target;

    protected Method method;

    protected Object[] arguments;

    public ReflectiveMethodInvocation(Object target, Method method, Object[] arguments) {
        this.target = target;
        this.method = method;
        this.arguments = arguments;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    /**
     * proceed()
     * 方法是调用原始对象的方法 method.invoke(object,args)。
     */
    @Override
    public Object proceed() throws Throwable {
        return method.invoke(target, arguments);
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }
}
```

- 代理类及接口：

> AopProxy : 代理对象生成器接口
```java
public interface AopProxy {
    /**
     * 为目标 Bean 生成代理对象
     * */
    Object getProxy();
}
```

> AbstractAopProxy
```java
public abstract class AbstractAopProxy implements AopProxy{
   // AdvisedSupport封装了TargetSource 、MethodInterceptor、MethodMatcher
    protected AdvisedSupport advised;

    public AbstractAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }
}
```

> JDKDynamicAopProxy : 基于 JDK 动态代理的代理对象生成器
```java
public class JDKDynamicAopProxy extends AbstractAopProxy implements InvocationHandler{
    public JDKDynamicAopProxy(AdvisedSupport advised) {
        super(advised);
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(getClass().getClassLoader(),
                advised.getTargetSource().getInterfaces(),this);
    }

    @Override
    public Object invoke(final Object proxy, Method method, final Object[] args) throws Throwable {
        Object target=advised.getTargetSource().getTarget();
        MethodMatcher methodMatcher = advised.getMethodMatcher();

        /**
         * 1. 使用方法匹配器 methodMatcher 测试 Bean 中原始方法 method 是否符合匹配规则
         */
        if (methodMatcher != null && methodMatcher.matches(method, target.getClass())) {
            // 获取 Advice (通知)。
            // MethodInterceptor 的父接口继承了 Advice
            MethodInterceptor methodInterceptor = advised.getMethodInterceptor();

           /**
            * 2. 将 Bean 的原始方法 method 封装在 MethodInvocation 接口实现类对象中，
            * 并把生成的对象作为参数传给 Adivce 实现类对象，执行通知逻辑
            */
            return methodInterceptor.invoke(
                    new ReflectiveMethodInvocation(target, method, args));
        } else {
            // 2. 当前 method 不符合匹配规则，直接调用 Bean 的原始方法 method
            return method.invoke(target, args);
        }
    }
}
```

JDKDynamicAopProxy的getProxy()方法中的执行流程：

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/03_5.png" width="200"/></div>

- 测试：

> IUserDao : 测试接口
```java
public interface IUserDao {
    void add();
    void delete();
    void update();
    void search();
}
```

> UserDao : 测试接口的实例类

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

>  LogIntercepter : 方法拦截器,用于对方法进行增强

```java
public class LogInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        //增强的代码1
        System.out.println(invocation.getMethod().getName() + " method start");
        Object obj= invocation.proceed();
        //增强的代码2
        System.out.println(invocation.getMethod().getName() + " method end");
        return obj;
    }
}
```

```java
public class JDKDynamicAopProxyTest {
    @Test
    public void test(){
        System.out.println("---------- no proxy ----------");
        IUserDao userDao= new UserDao();
        userDao.add();
        userDao.delete();

        System.out.println("\n----------- proxy -----------");
        AdvisedSupport advised = new AdvisedSupport();
        TargetSource targetSource = new TargetSource(
                userDao,UserDao.class,IUserDao.class);
        advised.setTargetSource(targetSource);
        advised.setMethodInterceptor(new LogInterceptor());

        //仅仅对add方法进行匹配
        advised.setMethodMatcher(
                new MethodMatcher() {
                    @Override
                    public boolean matches(Method method, Class targetClass) {
                        if("add".equals(method.getName())){
                            return true;
                        }
                        return false;
                    }
                }
        );


        userDao = (IUserDao)new JDKDynamicAopProxy(advised).getProxy();
        userDao.add();
        userDao.delete();
    }
}
```

输出结果：
```html
---------- no proxy ----------
添加功能
删除功能

----------- proxy -----------
add method start
添加功能
add method end
删除功能
```

### AOP 和 IOC 的整合

在 TinySpring-版本2 中，AOP 和 IOC 产生联系的具体实现类是
**AspectJAwareAdvisorAutoProxyCreator**，
这个类实现了**BeanPostProcessor**和**BeanFactoryAware**接口。
BeanFactory 在注册 BeanPostProcessor 接口相关实现类的阶段，
会将其本身注入到 AspectJAwareAdvisorAutoProxyCreator 中，为后面 AOP 给 Bean 生成代理对象做准备。
BeanFactory 初始化结束后，AOP 与 IOC 桥梁类 AspectJAwareAdvisorAutoProxyCreator 也完成了实例化，
并被缓存在 BeanFactory 中，静待 BeanFactory 实例化 Bean。
当外部产生调用，BeanFactory 开始实例化 Bean 时。AspectJAwareAdvisorAutoProxyCreator 就开始悄悄的工作了 ：

1. 从 BeanFactory 查找实现了 PointcutAdvisor 接口的**切面对象**，
切面对象中包含了实现 Pointcut 和 Advice 接口的对象。

2. 使用 Pointcut 中的表达式对象匹配当前 Bean 对象。如果匹配成功，进行下一步。
否则终止逻辑，返回 Bean。

3. JDKDynamicAopProxy 对象为匹配到的 Bean 生成代理对象，并将代理对象返回给 BeanFactory。

- Joinpoint(连接点)相关接口和类

> ClassFilter

```java
public interface ClassFilter {
    boolean matches(Class beanClass) throws Exception;
}
```

> MethodMatcher

```java
public interface MethodMatcher {
    /**
     * 匹配该方法是否是要拦截的方法
     */
    boolean matches(Method method, Class targetClass);
}
```

- Pointcut(切入点)相关类或接口

> Pointcut

```java
public interface Pointcut {
    ClassFilter getClassFilter();
    MethodMatcher getMethodMatcher();
}
```

- Aspect(切面) : 切入点和通知的结合

> Advisor : 代表一般切面，Advice本身就是一个切面，对目标类所有方法进行拦截(不带有切点的切面.针对所有方法进行拦截)
 
```java
public interface Advisor {
    Advice getAdvice();
}
```

> PointcutAdvisor : 代表具有切点的切面，可以指定拦截目标类哪些方法(带有切点的切面,针对某个方法进行拦截)
```java
public interface PointcutAdvisor extends Advisor{
    /**
     * 获取切点
     */
    Pointcut getPointcut();
}
```

> AspectJExpressionPointcut

```java
/**
 * 通过AspectJ表达式定义切点的切面类
 */
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {
    private PointcutParser pointcutParser;

    private String expression;

    private PointcutExpression pointcutExpression;

    private static final Set<PointcutPrimitive> DEFAULT_SUPPORTED_PRIMITIVES = new HashSet<>();

    static {
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.ARGS);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.REFERENCE);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.THIS);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.TARGET);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.WITHIN);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ANNOTATION);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_WITHIN);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ARGS);
        DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_TARGET);
    }

    public AspectJExpressionPointcut() {
        this(DEFAULT_SUPPORTED_PRIMITIVES);
    }

    public AspectJExpressionPointcut(Set<PointcutPrimitive> supportedPrimitives) {
        pointcutParser = PointcutParser
                .getPointcutParserSupportingSpecifiedPrimitivesAndUsingContextClassloaderForResolution(supportedPrimitives);
    }

    /**
     * 使用 AspectJ Expression 匹配类
     * @param targetClass
     * @return成功匹配返回 true，否则返回 false
     */
    @Override
    public boolean matches(Class targetClass) {
        checkReadyToMatche();
        return pointcutExpression.couldMatchJoinPointsInType(targetClass);
    }

    /**
     * 使用 AspectJ Expression 匹配方法
     * @param method
     * @param targetClass
     * @return 成功匹配返回 true，否则返回 false
     */
    @Override
    public boolean matches(Method method, Class targetClass) {
        checkReadyToMatche();
        ShadowMatch shadowMatch = pointcutExpression.matchesMethodExecution(method);

        // Special handling for this, target, @this, @target, @annotation
        // in Spring - we can optimize since we know we have exactly this class,
        // and there will never be matching subclass at runtime.
        // https://github.com/spring-projects/spring-framework/blob/master/spring-aop/src/main/java/org/springframework/aop/aspectj/AspectJExpressionPointcut.java
        if (shadowMatch.alwaysMatches()) {
            return true;
        }
        else if (shadowMatch.neverMatches()) {
            return false;
        }

        return false;
    }

    private void checkReadyToMatche() {
        if (getExpression() == null) {
            throw new IllegalStateException("Must set property 'expression' before attempting to match");
        }
        if (pointcutExpression == null) {
            pointcutExpression = pointcutParser.parsePointcutExpression(expression);
        }
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }
}
```
> AspectJAwareAdvisorAutoProxyCreator

```java
public class AspectJAwareAdvisorAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware {

    private XmlBeanFactory xmlBeanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        /*
         * 这里两个 if 判断很有必要，如果删除将会使程序进入死循环状态，
         * 最终导致 StackOverflowError 错误发生
         */
        if (bean instanceof AspectJExpressionPointcutAdvisor) {
            return bean;
        }
        if (bean instanceof MethodInterceptor) {
            return bean;
        }

        // 1. 从 BeanFactory 查找 AspectJExpressionPointcutAdvisor 类型的对象
        List<AspectJExpressionPointcutAdvisor> advisors =
                xmlBeanFactory.getBeansForType(AspectJExpressionPointcutAdvisor.class);
        for (AspectJExpressionPointcutAdvisor advisor : advisors) {

            // 2. 使用 Pointcut 对象匹配当前 bean 对象
            if (advisor.getPointcut().getClassFilter().matches(bean.getClass())) {
                ProxyFactory advisedSupport = new ProxyFactory();
                advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
                advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

                TargetSource targetSource = new TargetSource(bean, bean.getClass(), bean.getClass().getInterfaces());
                advisedSupport.setTargetSource(targetSource);

                // 3. 生成代理对象，并返回
                return advisedSupport.getProxy();
            }
        }

        // 2. 匹配失败，返回 Bean
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws Exception {
        xmlBeanFactory = (XmlBeanFactory) beanFactory;
    }
}
```

### [TinySpring-版本2 相关代码](https://github.com/DuHouAn/Java-Notes/tree/master/Spring/TinySpring2)