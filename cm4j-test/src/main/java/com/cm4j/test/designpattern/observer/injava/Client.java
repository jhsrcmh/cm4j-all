package com.cm4j.test.designpattern.observer.injava;

import java.util.Observable;
import java.util.Observer;

public class Client {

	/**
	 * JDK版 - 和标准版本实现一样，只是JDK帮助实现了2个接口 Observer 和 Observable 
	 * 1vn 观察者模式的本质：将观察者聚合进被观察者，调用被观察者的方法同时调用观察者的方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 观察者
		Observer obs = new ConcreteObserver();
		// 被观察者
		ConcreteObservable subject = new ConcreteObservable();
		subject.addObserver(obs);
		
		// 内部类添加观察者
		subject.addObserver(new Observer(){
			@Override
			public void update(Observable o, Object arg) {
				Object[] obj = (Object[]) arg;
				for (Object object : obj) {
					System.out.println("innerClass:" + object);
				}
			}
		});

		subject.doSomething(new String[] { "a", "b" });
	}
}
