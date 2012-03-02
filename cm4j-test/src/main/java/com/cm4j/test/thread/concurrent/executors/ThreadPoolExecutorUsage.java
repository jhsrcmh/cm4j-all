package com.cm4j.test.thread.concurrent.executors;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorUsage {

	class R extends Thread {
		@Override
		public void run() {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("name:" + getName());
		}
	}
	
	public void execTask (){
		ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 1, TimeUnit.SECONDS	, new LinkedBlockingQueue<Runnable>());
		executor.execute(new R());
		executor.execute(new R());
		executor.execute(new R());
	
		System.out.println(executor.isShutdown());
		executor.shutdown();
		System.out.println(executor.isTerminated());
		System.out.println(executor.isTerminating());
	}
	
	public static void main(String[] args) {
		ThreadPoolExecutorUsage usage = new ThreadPoolExecutorUsage();
		usage.execTask();
	}
}
