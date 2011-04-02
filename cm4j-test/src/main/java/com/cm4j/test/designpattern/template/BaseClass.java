package com.cm4j.test.designpattern.template;

public abstract class BaseClass {
	public void process() {
		System.out.println("公共代码1");
		method1();
		System.out.println("公共代码2");
		method2();
		System.out.println("公共代码3");
	}
	abstract void method1();
	abstract void method2();
}
