package com.cm4j.test.designpattern.memento.multi;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 获取和设置对象属性，类似功能apache commons，spring也都有提供
 * 
 * @author yang.hao
 * @since 2011-5-19 下午05:17:17
 * 
 */
public class BeanUtils {
	/**
	 * 备份对象属性到map中
	 * @param bean
	 * @return
	 */
	public static HashMap<String, Object> backupProp(Object bean) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
			// 遍历所有属性
			for (PropertyDescriptor des : descriptors) {
				// 属性名
				String fieldName = des.getName();
				// 读取属性的方法
				Method getter = des.getReadMethod();
				// 读取属性值
				Object fieldValue = getter.invoke(bean, new Object[] {});
				
				if (!fieldName.equalsIgnoreCase("class"))
					result.put(fieldName, fieldValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 从map中恢复对象属性
	 * @param bean
	 * @param propMap
	 */
	public static void restoreProp(Object bean, HashMap<String, Object> propMap) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor des : descriptors) {
				// 属性名
				String fieldName = des.getName();
				if (propMap.containsKey(fieldName)) {
					Method setter = des.getWriteMethod();
					setter.invoke(bean, new Object[] { propMap.get(fieldName) });
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
