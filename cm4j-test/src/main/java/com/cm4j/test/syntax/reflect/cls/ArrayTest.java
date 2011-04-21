package com.cm4j.test.syntax.reflect.cls;

import java.lang.reflect.Array;

import org.apache.commons.lang.ArrayUtils;

public class ArrayTest {
	/**
	 * 反射创建数组并拷贝
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String[] oldArray = new String[] { "a", "b", "c" };
		// 获取数组的Class，注意必须加getComponentType()
		Class<?> cls = oldArray.getClass().getComponentType();
		// 根据class创建新实例
		Object newArray = Array.newInstance(cls, oldArray.length);
		// 数组拷贝
		System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
		System.out.println(ArrayUtils.toString(newArray));
	}
}
