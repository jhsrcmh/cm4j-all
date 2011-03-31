package com.cm4j.test.designpattern.callback;

public class FooBar {

	/*
	 * 我没有实现接口，但是我取得了一个实现接口的对象，而这个对象是其他类调用我的方法（ setCallBack ()）
	 * 时所赋给我的，因此我可以在业务需要的地方来调用实现接口的类里面的方法
	 */
	public void doSth(ICallBack callBack,int i) {
		try {
			callBack.postExec(i);
		} catch (RuntimeException e) {
			// 异常捕获
			e.printStackTrace();
		}
	}
}