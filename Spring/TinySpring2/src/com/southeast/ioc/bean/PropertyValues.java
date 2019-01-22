package com.southeast.ioc.bean;

import java.util.ArrayList;
import java.util.List;

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
