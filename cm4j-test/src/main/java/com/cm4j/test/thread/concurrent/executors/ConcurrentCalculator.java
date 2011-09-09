package com.cm4j.test.thread.concurrent.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.ArrayUtils;

/**
 * 根据计算机CPU个数分为多个线程并发计算数组总和
 * 
 * @author yang.hao
 * @since 2011-9-7 下午4:26:22
 */
public class ConcurrentCalculator {
	private int cpuCount;

	public ConcurrentCalculator() {
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
		ExecutorService service = Executors.newFixedThreadPool(cpuCount);
		int[] subarray1 = ArrayUtils.subarray(array, 0, 5);
		int[] subarray2 = ArrayUtils.subarray(array, 5, 10);
		Future<Integer> future1 = service.submit(new HandlerThread(subarray1));
		Future<Integer> future2 = service.submit(new HandlerThread(subarray2));

		Integer result1 = future1.get();
		Integer result2 = future2.get();
		return result1 + result2;
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ConcurrentCalculator calculator = new ConcurrentCalculator();
		int result = calculator.getResult(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 });
		System.out.println(result);
	}
}
