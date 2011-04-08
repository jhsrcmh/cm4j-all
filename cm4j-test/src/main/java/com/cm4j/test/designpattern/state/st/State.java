package com.cm4j.test.designpattern.state.st;

import com.cm4j.test.designpattern.state.Context;

public abstract class State {

	protected Lift lift = new Lift();
	
	protected Context ctx;
	
	public abstract void open();

	public abstract void close();

	public void setCtx(Context ctx) {
		this.ctx = ctx;
	}
}
