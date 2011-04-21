package com.cm4j.test.syntax.reflect.cls;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwoString {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String m_s1, m_s2;

	protected TwoString(String s1, String s2) {
		m_s1 = s1;
		m_s2 = s2;
	}

	public void print() {
		logger.debug("m_s1:{},m_s2:{}", m_s1, m_s2);
	}

	/**
	 * 修改对象的字段值，在原值后面追加_extend
	 * 
	 * @param name
	 * @param obj
	 * @throws Exception
	 */
	public void changeFieldWithFeild(String name, Object obj) throws Exception {
		// 获取Field对象
		Field field = obj.getClass().getDeclaredField(name);
		// 获取字段值并 + "_extend"
		String value = (String) field.get(obj) + "_extend";
		// 设置字段值
		field.set(obj, value);
	}

	/**
	 * 通过调用getter setter方法修改字段值 - 在原值后面追加_extend
	 * 
	 * @param name
	 * @param obj
	 * @throws Exception
	 */
	public void changeFieldWithMethod(String name, Object obj) throws Exception {
		// 方法名
		String getterName = "get" + StringUtils.capitalize(name);
		String setterName = "set" + StringUtils.capitalize(name);
		// 获取方法
		Method getterMethod = obj.getClass().getDeclaredMethod(getterName, new Class[0]);
		Method setterMethod = obj.getClass().getDeclaredMethod(setterName, new Class[] { String.class });
		// 调用方法
		Object value = getterMethod.invoke(obj, new Object[0]);
		setterMethod.invoke(obj, new Object[] { value + "_extend" });
	}

	public String getM_s1() {
		return m_s1;
	}

	public void setM_s1(String m_s1) {
		this.m_s1 = m_s1;
	}

	public String getM_s2() {
		return m_s2;
	}

	public void setM_s2(String m_s2) {
		this.m_s2 = m_s2;
	}

	/**
	 * 内省
	 */
	public void introspectorTest() throws Exception {
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(m_s1, TwoString.class);
		Method setter = propertyDescriptor.getWriteMethod();
		Method getter = propertyDescriptor.getReadMethod();
		TwoString ts = new TwoString(null, null);
		setter.invoke(ts, "a");
		System.out.println(getter.invoke(ts, new Object[0]));
	}

	public static void main(String[] args) throws Exception {
		// 根据参数类型获取构造函数
		Constructor<TwoString> cons = TwoString.class
				.getDeclaredConstructor(new Class[] { String.class, String.class });
		System.out.println(cons.toGenericString());
		// 由构造函数实例化出一个对象
		TwoString ts = (TwoString) cons.newInstance(new Object[] { "a", "b" });
		ts.print();
		ts.changeFieldWithFeild("m_s1", ts);
		ts.print();
		ts.changeFieldWithFeild("m_s2", ts);
		ts.print();
	}
}
