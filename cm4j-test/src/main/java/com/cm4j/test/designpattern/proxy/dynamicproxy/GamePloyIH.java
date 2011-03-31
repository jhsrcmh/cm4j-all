package com.cm4j.test.designpattern.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class GamePloyIH implements InvocationHandler {

	/**
	 * 被代理实例
	 */
	Object obj = null;

	// 构造函数，将被代理对象聚合进来
	public GamePloyIH(Object _obj) {
		this.obj = _obj;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		// 调用具体类执行方法
		Object result = method.invoke(this.obj, args);

		// 在具体invoke前后可以增加方法，做类似AOP的前后调用
		if (method.getName().equalsIgnoreCase("login"))
			System.out.println("调用登陆1次");
		return result;
	}

}
