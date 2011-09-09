package com.cm4j.test.thread.concurrent.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.ArrayUtils;

public class CompletionServiceUsage {
	private int cpuCount;

	public CompletionServiceUsage() {
		this.cpuCount = Runtime.getRuntime().availableProcessors();
	}

	class HandlerThread implements Callable<Integer> {
		private int[] nums;

		public HandlerThread(int[] nums) {
			super();
			this.nums = nums;
		}

		@Override
		public Integer call() throws Exception {
			int result = 0;
			for (int num : nums) {
				result += num;
			}
			return result;
		}
	}

	/**
	 * 将array按照CPU数量分成几个子array给线程单独去执行，完成后再把结果合计<br/>
	 * 下面程序实现的时候假定CPU为双核的即cpuCount=2
	 * 
	 * @param array
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public int getResult(int[] array) throws InterruptedException, ExecutionException {
		CompletionService<Integer> service = new ExecutorCompletionService<Integer>(Executors.newFixedThreadPool(cpuCount));
		int[] subarray1 = ArrayUtils.subarray(array, 0, 5);
		int[] subarray2 = ArrayUtils.subarray(array, 5, 10);
		service.submit(new HandlerThread(subarray1));
		service.submit(new HandlerThread(subarray2));

		Integer result1 = service.take().get();
		Integer result2 = service.take().get();
		return result1 + result2;
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		CompletionServiceUsage calculator = new CompletionServiceUsage();
		int result = calculator.getResult(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 });
		System.out.println(result);
	}
}
