package com.cm4j.test.designpattern.composite;

import java.util.ArrayList;

public class Branch extends Corp implements IBranch {

	public Branch(String name, String position, int salary) {
		super(name, position, salary);
	}

	ArrayList<Corp> list = new ArrayList<Corp>();

	@Override
	public void add(Corp corp) {
		this.list.add(corp);
		corp.setParent(this);
	}

	@Override
	public ArrayList<Corp> getSubordinateInfo() {
		return this.list;
	}

}
