package com.cm4j.test.syntax.reflect.cls;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassTest {
	private static Logger logger = LoggerFactory.getLogger(ClassTest.class);

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		args = new String[] { "java.util.LinkedList" }; // 有内部类
		// args = new String[] { "java.util.LinkedList$DescendingIterator" }; //
		// 有外部类

		// 1.找出class 隶属的package。
		Class<?> c = Class.forName(args[0]);
		Package pkg = c.getPackage();
		logger.debug("包:{}", pkg.getName());

		// 2.找出class或interface的名称，及其属性（modifiers）。
		StringBuffer sb = new StringBuffer();
		int modifier = c.getModifiers();
		sb.append(Modifier.toString(modifier));
		if (Modifier.isInterface(modifier))
			sb.append(" ");// 关键词 "interface" 已含于modifier
		else
			sb.append(" class ");// 不是接口就是类
		sb.append(c.getSimpleName());
		logger.debug("修饰符:{}", sb.toString());

		// 3.找出base class(父类)
		Class<?> superclass = c.getSuperclass();
		if (superclass != null)
			logger.debug("父类：{}", superclass.getName());

		// 4.找出实现接口
		Class<?>[] interfaces = c.getInterfaces();
		for (Class<?> inface : interfaces) {
			logger.debug("实现接口：{}", inface.getName());
		}

		// 5.找出inner classes 和outer class
		Class<?>[] declaredClasses = c.getDeclaredClasses();
		Class<?> declaringClass = c.getDeclaringClass();
		for (Class<?> declaredClass : declaredClasses) {
			logger.debug("内部类:{}", declaredClass);
		}
		if (declaringClass != null) {
			logger.debug("外部类:{}", declaringClass);
		}

		// 6.找出所有methods
		Method[] declaredMethods = c.getDeclaredMethods();
		for (Method method : declaredMethods) {
			sb = new StringBuffer();
			int modifiers = method.getModifiers();
			sb.append(Modifier.toString(modifiers));
			// 方法返回值 - 带范型
			Type returnType = method.getGenericReturnType();
			sb.append(" ").append(
					returnType instanceof Class ? ((Class<?>) returnType).getSimpleName() : returnType.toString());
			sb.append(" ").append(method.getName()).append("(");
			// 方法参数 - 带范型
			Type[] parameterTypes = method.getGenericParameterTypes();
			for (Type type : parameterTypes) {
				sb.append(type instanceof Class ? ((Class<?>) type).getSimpleName() : type.toString()).append(",");
			}
			// 删除最后一个参数后的逗号
			if (parameterTypes.length > 0)
				sb.deleteCharAt(sb.lastIndexOf(","));
			sb.append(")");

			logger.debug("方法:{}", sb.toString());
			logger.debug("方法:{}", method.toGenericString() + "\n");
		}

		// 7.找出所有构造函数
		c.getDeclaredConstructors();

		// 8.找出所有的方法
		c.getDeclaredMethods();

		// 9.找出所有的字段
		c.getDeclaredFields();

		/**
		 * ===============范型相关=============
		 */
		// 10.找出parameterized types(范型)的名称
		TypeVariable<?>[] typeParameters = c.getTypeParameters();
		for (TypeVariable<?> typeVariable : typeParameters) {
			logger.debug("范型:{}", typeVariable.getName());
		}

		// 获取带范型的接口
		Type[] genericInterfaces = c.getGenericInterfaces();
		// 转化为 参数化类型 -> List<E>
		ParameterizedType parameterizedType = (ParameterizedType) genericInterfaces[0];
		if (genericInterfaces.length > 0) {
			logger.debug("第一个范型父接口:{}", parameterizedType);
			// getActualTypeArguments()返回的实际上就是TypeVariableImpl
			logger.debug("参数化类型 - 代表范型：{}", (TypeVariable<?>) parameterizedType.getActualTypeArguments()[0]);
			logger.debug("参数化类型 - 代表的类：{}", parameterizedType.getRawType());
		}

		// 问题：下面为什么第一种获得的是E，而第二种获得的是对象？
		// 解答：getGenericInterfaces()获得的是本类直接实现的接口，只由本类的定义所决定，和具体对象无关，也和接口定义无关
		Father<Object> fx = new ClassTest().new Son<Object>();
		Class<?> cls = fx.getClass();
		logger.debug("Son参数化类型 - 代表范型：{}",
				((ParameterizedType) cls.getGenericInterfaces()[0]).getActualTypeArguments()[0]);
		Father fx2 = new ClassTest().new Son2();
		Class cls2 = fx2.getClass();
		logger.debug("Son2参数化类型 - 代表范型：{}",
				((ParameterizedType) cls2.getGenericInterfaces()[0]).getActualTypeArguments()[0]);
	}

	interface Father<E> {
	}

	class Son<E> implements Father<E> {
	}

	class Son2 implements Father<Object> {
	}
}
