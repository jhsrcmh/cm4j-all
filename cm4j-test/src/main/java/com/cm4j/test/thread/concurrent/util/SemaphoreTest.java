package com.cm4j.test.thread.concurrent.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 模拟简单的线程池
 * 
 * @author yang.hao
 * @since 2011-8-29 下午3:00:59
 */
public class SemaphoreTest {

	private int max = 2;
	private List<Object> list;
	private Semaphore semaphore;
	private AtomicLong counter;

	public SemaphoreTest() {
		list = new ArrayList<Object>();
		semaphore = new Semaphore(max);
		counter = new AtomicLong();
		for (int i = 0; i < max; i++) {
			list.add(i);
		}
	}

	/**
	 * release()可无限调用，因此建议用下面方法来进行资源获取和释放
	 * 
	 * <pre>
	 * try {
	 * 	result = get();
	 * 	System.out.println(&quot;result:&quot; + result);
	 * } catch (InterruptedException e) {
	 * 	e.printStackTrace();
	 * } finally {
	 * 	return2Pool(result);
	 * }
	 * </pre>
	 * 
	 * @param obj
	 */
	public void return2Pool(Object obj) {
		if (obj != null) {
			semaphore.release(1);
			System.out.println("avaliable permits:" + semaphore.availablePermits());
			list.add(obj);
		}
	}

	public Object get() throws InterruptedException {
		System.out.println("get time:" + counter.incrementAndGet());
		if (semaphore.tryAcquire(1, TimeUnit.SECONDS)) {
			Object obj = list.get(0);
			list.remove(obj);
			return obj;
		}
		return null;
	}

	public static void main(String[] args) {
		SemaphoreTest test = new SemaphoreTest();
		T t = test.new T();
		new Thread(t).start();
		new Thread(t).start();
		new Thread(t).start();
	}

	class T implements Runnable {
		@Override
		public void run() {
			Object result = null;
			while (true) {
				try {
					result = get();
					System.out.println("result:" + result);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					return2Pool(result);
				}
			}
		}
	}
}
