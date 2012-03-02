package com.cm4j.test.thread.concurrent.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * <pre>
 * task submitter把任务交给Executor执行，他们之间需要一种通讯手段，具体实现即Future。
 * Future通常包括get(阻塞至任务完成)，cancel，get(timeout)等
 * 
 * 有两种任务：Runnable Callable(需要返回值的任务)
 * </pre>
 * 
 * @author yanghao
 * 
 */
public class FutureTaskWithExecutorService {

	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
		// 1.定义Callable
		Callable<Object> task = new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				Thread.sleep(3000);
				return "result object";
			}
		};

		// Executors是Executor的工厂类
		ExecutorService executor = Executors.newSingleThreadExecutor();
		// 2.ExecutorService管理线程的生命周期，因此通过submit可以获得FutureTask
		Future<Object> future = null;
		if (!executor.isShutdown()) {
			future = executor.submit(task);
		}
		// 4.从futureTask获取值
		System.out.println(future.get(1, TimeUnit.SECONDS));
		executor.shutdown();
	}
}
