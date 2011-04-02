package com.cm4j.test.designpattern.observer.injava;

import java.util.Observable;
import java.util.Observer;

public class ConcreteObserver implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		Object[] obj = (Object[]) arg;
		for (Object object : obj) {
			System.out.println(object);
		}
	}

}
