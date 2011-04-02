package com.cm4j.test.designpattern.callback;

public class Test {

	public static void main(String[] args) {

		FooBar foo = new FooBar();
		/**
		 * 注意下面的这项代码片段，它给foo对象传递了一个实现ICallBack接口的匿名类，这样FooBar类的对象就取
		 * 
		 * 得了一个实现接口的类，因此FooBar可以在任何时候调用接口中的方法
		 */
		
		// 调用
		foo.doSth(new ICallBack() {
			public void postExec(int i) {
				System.out.println("我(postExec)是在Test类中实现的，但我不能被Test的对象引用," +
				"而由FooBar对象调用");
				System.out.println("参数：" + i);
			}
		},100);
	}
}
