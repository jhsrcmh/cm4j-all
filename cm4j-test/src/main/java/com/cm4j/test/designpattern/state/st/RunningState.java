package com.cm4j.test.designpattern.state.st;


public class RunningState extends State {

	@Override
	public void open() {
		System.out.println("error:lift is running");
	}

	@Override
	public void close() {
		System.out.println("error:lift is running");
	}

}
