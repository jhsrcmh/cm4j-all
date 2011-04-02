package com.cm4j.test.designpattern.observer.standard;

/**
 * 被观察者
 * @author yanghao
 *
 */
public class ConcreteSubject extends Subject {
	
	/**
	 * 在被观察方法内通知观察者
	 */
	public void doSomething(){
		// 通知观察者
		super.notifyObservers("a","b");
	}
}
