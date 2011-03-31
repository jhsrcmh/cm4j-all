package com.cm4j.test.designpattern.observer.one2one;

public class Client {

	/**
	 * 初始版本 - 1v1
	 * 观察者模式的本质：将观察者聚合进被观察者，调用被观察者的方法同时调用观察者的方法
	 * @param args
	 */
	public static void main(String[] args) {
		IHanFeiZi hanFeiZi = new HanFeiZiImpl();
		hanFeiZi.haveBreakfast();
		hanFeiZi.haveFun();
	}
}
