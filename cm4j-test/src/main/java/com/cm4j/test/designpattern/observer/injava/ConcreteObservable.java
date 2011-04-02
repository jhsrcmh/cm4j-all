package com.cm4j.test.designpattern.observer.injava;

import java.util.Observable;

public class ConcreteObservable extends Observable {

	public void doSomething(String ... args) {
		System.out.println("被观察者做自己的事");
		super.setChanged(); // jdk先判断该instance是否变化，变化了才会通知
		super.notifyObservers(args);
	}
}
