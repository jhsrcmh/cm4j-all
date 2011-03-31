package com.cm4j.test.designpattern.observer.standard;

public class Client {

	/**
	 * 标准版本 - 1vn
	 * 观察者模式的本质：将观察者聚合进被观察者，调用被观察者的方法同时调用观察者的方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 观察者
		Observer obs = new ConcreteObserver();
		// 被观察者
		ConcreteSubject subject = new ConcreteSubject();
		subject.addObserver(obs);

		subject.doSomething();
	}
}
