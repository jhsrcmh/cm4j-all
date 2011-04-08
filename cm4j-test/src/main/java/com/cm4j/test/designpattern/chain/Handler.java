package com.cm4j.test.designpattern.chain;

public abstract class Handler {
	/**
	 * 抽象方法 - 子类去各自实现处理
	 * 
	 * @param woman
	 */
	protected abstract void handle(IWoman woman);

	private Handler next;

	public void setNext(Handler handler) {
		this.next = handler;
	}

	public final void handleMessage(IWoman woman) {
		// woman指定的处理类型，则当前处理
		if (getClass() == woman.getHandler())
			this.handle(woman);
		// woman没指定类型，但next不为空，则传递给next处理
		else if (this.next != null)
			this.next.handleMessage(woman);
		else
			System.out.println("request not match");
	}
}
