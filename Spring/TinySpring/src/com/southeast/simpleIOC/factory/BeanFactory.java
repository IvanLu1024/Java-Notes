package com.southeast.simpleIOC.factory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 容器实现类
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
        // 加载 xml 配置文件
        InputStream inputStream = new FileInputStream(location);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputStream);
        //root标签是"<beans>"
        Element root = doc.getDocumentElement();
        NodeList nodes = root.getChildNodes();

        // 遍历 <bean> 标签
        //将在.xml文件中申明的所有Bean都注册到容器中
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
