package com.cm4j.test.designpattern.composite;

/**
 * 为了抽象共有代码，不然这个类都可以去除了
 * 
 * @author yang.hao
 * @since 2011-5-16 下午05:16:12
 *
 */
public abstract class Corp {

	private String name, position;
	private int salary;
	// 可向上查找
	private Corp parent;

	public Corp(String name, String position, int salary) {
		this.name = name;
		this.position = position;
		this.salary = salary;
	}
	
	public String getInfo() {
		String info = "name:" + name + ",position:" + position + ",salary:" + salary;
		return info;
	}

	public void setParent(Corp parent) {
		this.parent = parent;
	}

	public Corp getParent() {
		return parent;
	}
}
