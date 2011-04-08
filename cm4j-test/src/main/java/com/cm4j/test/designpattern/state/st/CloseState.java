package com.cm4j.test.designpattern.state.st;

import com.cm4j.test.designpattern.state.Context;

public class CloseState extends State{

	@Override
	public void open() {
		super.lift.open();
		ctx.setState(Context.OPEN_STATE);
	}

	@Override
	public void close() {
		System.out.println("error:lift is closing");
	}

}
