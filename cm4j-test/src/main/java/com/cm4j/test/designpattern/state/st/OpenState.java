package com.cm4j.test.designpattern.state.st;

import com.cm4j.test.designpattern.state.Context;

public class OpenState extends State {

	@Override
	public void open() {
		System.out.println("error:list is opening");
	}

	@Override
	public void close() {
		super.lift.close();
		ctx.setState(Context.CLOSE_STATE);
	}

}
