package com.cm4j.test.designpattern.observer.standard;

public class ConcreteObserver implements Observer {

	@Override
	public void update(Object... args) {
		for (Object o : args)
			System.out.println(o);
	}

}
