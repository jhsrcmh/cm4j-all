package com.cm4j.taobao.api.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.taobao.api.internal.mapping.ApiField;

/**
 * 淘宝domain对象解析
 * 
 * @author yang.hao
 * @since 2011-7-28 上午10:23:19
 * 
 */
public class DomainResolver {

	/**
	 * 获取某类的注解ApiField的值
	 * 
	 * @param cls
	 * @param annotaion
	 * @return
	 */
	public static List<String> getApiFieldValues(Class<?> cls) {
		List<String> fieldNames = new ArrayList<String>();

		Field[] declaredFields = cls.getDeclaredFields();
		for (Field field : declaredFields) {
			ApiField annotation = field.getAnnotation(ApiField.class);
			if (annotation != null) {
				fieldNames.add(annotation.value());
			}
		}
		return fieldNames;
	}
}
