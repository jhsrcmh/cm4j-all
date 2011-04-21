package com.cm4j.test.syntax.reflect.cls;

import java.lang.reflect.Field;

public class ReflectSecurity {

	/**
	 * 反射安全性
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		TwoString ts = new TwoString("a", "b");
		Field field = ts.getClass().getDeclaredField("m_s1");
		// 如果注释此句，m_s1的访问权限和普通调用一样，运行抛IllegalAccessException
		// 如果不注释，则可访问m_s1，运行正常，尽管m_s1是private的
		// 如果您在命令行添加了JVM参数 -Djava.security.manager 以实现安全性管理器，它将再次失败，
		// 除非您定义了ReflectSecurity 类的许可权限。
		// field.setAccessible(true);
		System.out.println(field.get(ts));
	}
}
