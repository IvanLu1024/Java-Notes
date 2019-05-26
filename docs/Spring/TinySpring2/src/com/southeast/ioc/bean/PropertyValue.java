package com.southeast.ioc.bean;

/**
 * PropertyValue 中有两个字段 name 和 value，
 * 用于记录 bean 配置中的标签的属性值。
 */
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
