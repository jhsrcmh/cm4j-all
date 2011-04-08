package com.cm4j.test.designpattern.chain;

/**
 * 责任链模式 - woman请求逛街，father-husband-son依次答复
 * 
 * @author yang.hao
 * @since 2011-4-7 下午04:30:35
 * 
 */
public class Client {

	public static void main(String[] args) {

		Handler father = new Father();
		Handler husband = new Husband();
		Handler son = new Son();

		// father->husband->son
		father.setNext(husband);
		husband.setNext(son);

		IWoman iWoman = new Woman(Son.class, "我要去逛啊");
		father.handleMessage(iWoman);

		IWoman iWoman2 = new Woman(Father.class, "我要去逛啊2");
		father.handleMessage(iWoman2);
	}
}
