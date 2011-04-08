package com.cm4j.test.designpattern.state;

/**
 * <pre>
 * 状态模式
 * 
 * 先设置当前状态，之后操作的状态都由State内部维护
 * 失败思路：程序运行到这里，是什么状态？
 * 新思路：在具有这些状态的时候，程序可以做什么？
 * </pre>
 * 
 * @author yang.hao
 * @since 2011-4-7 上午11:23:56
 * 
 */
public class Client {

	public static void main(String[] args) {
		Context ctx = new Context();
		// 假定当前状态为关闭
		ctx.setState(Context.CLOSE_STATE);
		// 调用者完全不用考虑内部状态是如何的，只需要调用相关代码
		ctx.openDoor();
		ctx.closeDoor();

		ctx.closeDoor();
		ctx.openDoor();

		ctx.openDoor();
		ctx.closeDoor();
	}
}
