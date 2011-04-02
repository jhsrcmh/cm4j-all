package com.cm4j.test.designpattern.observer.one2one;

public class LisiImpl implements ILisi {

	@Override
	public void update(String str) {
		System.out.println("Lisi 监听到:" + str);
	}

}
