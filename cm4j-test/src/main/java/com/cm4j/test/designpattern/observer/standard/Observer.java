package com.cm4j.test.designpattern.observer.standard;

/**
 * 观察者
 * @author yanghao
 *
 */
public interface Observer {

	/**
	 * 观察者方法
	 * @param args
	 */
	void update(Object... args);
}
