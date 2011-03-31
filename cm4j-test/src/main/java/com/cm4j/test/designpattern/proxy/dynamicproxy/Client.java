package com.cm4j.test.designpattern.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Client {

	public static void main(String[] args) {
		IGamePlayer player = new GamePlayer("张三");

		// proxy所需参数1 - 被代理类的ClassLoader
		ClassLoader clsLoader = player.getClass().getClassLoader();

		// proxy所需参数2 - 被代理类的接口数组
		@SuppressWarnings("rawtypes")
		Class[] cls_interfaces = player.getClass().getInterfaces();
		// proxy所需参数3 - 处理器Handler (实现JDK的InvocationHandler接口)
		InvocationHandler handler = new GamePloyIH(player);

		// 代理对象
		IGamePlayer proxy = (IGamePlayer) Proxy.newProxyInstance(clsLoader, cls_interfaces, handler);

		// 调用代理执行方法
		proxy.login("yh", "xyz");
		proxy.killBoss();
		proxy.upgrade();
	}
}
