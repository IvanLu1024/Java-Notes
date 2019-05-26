package com.southeast.ioc.xml;

import com.southeast.ioc.bean.BeanDefinition;
import com.southeast.ioc.bean.BeanReference;
import com.southeast.ioc.bean.PropertyValue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 18351 on 2019/1/17.
 */
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
        registry.put(name, beanDefinition);
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
