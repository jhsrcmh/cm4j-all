package com.cm4j.test.thread.concurrent.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceUsage {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		ScheduledFuture<Integer> future = scheduler.schedule(new Callable<Integer>() {
			private int i = 0;

			@Override
			public Integer call() throws Exception {
				return ++i;
			}
		}, 3, TimeUnit.SECONDS);
		
		Integer result = future.get();
		System.out.println(result);
	}
}
