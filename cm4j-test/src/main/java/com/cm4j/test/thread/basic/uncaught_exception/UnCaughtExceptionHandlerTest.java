package com.cm4j.test.thread.basic.uncaught_exception;

/**
 * 设置线程未捕获异常处理
 * 
 * @author yang.hao
 * @since 2011-8-25 上午11:36:15
 */
public class UnCaughtExceptionHandlerTest {

	public static class R implements Runnable {
		@Override
		public void run() {
			throw new IllegalArgumentException();
		}
	}

	public static void main(String[] args) {
		Thread t = new Thread(new R());
		t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("errorInfo:" + e.getClass());
			}
		});
		t.start();
	}
}
