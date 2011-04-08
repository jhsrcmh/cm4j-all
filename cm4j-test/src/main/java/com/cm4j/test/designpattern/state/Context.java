package com.cm4j.test.designpattern.state;

import com.cm4j.test.designpattern.state.st.CloseState;
import com.cm4j.test.designpattern.state.st.OpenState;
import com.cm4j.test.designpattern.state.st.RunningState;
import com.cm4j.test.designpattern.state.st.State;

/**
 * 封装类，目的：内部状态变化不被调用类知晓(迪米特法则)
 * 
 * @author yang.hao
 * @since 2011-4-7 下午02:17:29
 * 
 */
public class Context {

	private State state;

	// 定义常量，不用每次该状态都new一个对象出来
	public static final State OPEN_STATE = new OpenState();
	public static final State RUNNING_STATE = new RunningState();
	public static final State CLOSE_STATE = new CloseState();

	public void openDoor() {
		this.state.open();
	}

	public void closeDoor() {
		this.state.close();
	}

	/**
	 * 设置状态
	 * 
	 * @param state
	 */
	public void setState(State state) {
		this.state = state;
		// state.setCtx()方法持有this引用，其目的是为了让state类可以修改状态 - ctx.setState()
		this.state.setCtx(this);
	}

}
