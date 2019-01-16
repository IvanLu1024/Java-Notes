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

#### 1.容器实现类
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

#### 2.Bean
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

### [TinySpring-版本1 相关代码]()

## TinySpring-版本2


### [TinySpring-版本2 相关代码]()