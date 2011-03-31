package com.cm4j.test.designpattern.observer.standard;

import java.util.Vector;

/**
 * 被观察者父类 - 提供对观察者的增删以及调用方法
 * @author yanghao
 *
 */
public abstract class Subject {
	// 定义一个观察者数组
	/**
	 * @uml.property  name="obsVector"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="com.woniu.designpattern.observer.standard.Observer"
	 */
	private Vector<Observer> obsVector = new Vector<Observer>();
	
	// 增加一个观察者
	public void addObserver(Observer o) {
		this.obsVector.add(o);
	}
	
	// 删除一个观察者
	public void deleObserver (Observer o){
		this.obsVector.remove(o);
	}
	
	// 调用观察者方法 - 一般不用for循环，而用多线程异步调用
	public void notifyObservers(Object... args){
		for (Observer o : obsVector){
			o.update(args);
		}
	}
}
