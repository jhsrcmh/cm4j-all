package com.cm4j.test.thread.concurrent.coordination.countdown_barrier;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Self_BarrierTest {

	private int count;
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	public Self_BarrierTest(int count) {
		this.count = count;
	}

	public void await(boolean timeout, long timeoutnano) throws InterruptedException {
		lock.lock();
		try {
			int c = --count;
			if (c == 0) {
				condition.signalAll();
			}

			if (c > 0) {
				if (!timeout) {
					condition.await();
				} else {
					condition.await(timeoutnano, TimeUnit.NANOSECONDS);
				}
			}
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		int threadNum = 2;
		final Self_BarrierTest barr = new Self_BarrierTest(threadNum + 1);

		class Bow implements Runnable {

			public void run() {
				System.out.println("The bow is coming -> then kick a down");
				try {
					barr.await(false, 0L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("do other thing");
			}
		}
		for (int i = 0; i < threadNum; i++) {
			new Thread(new Bow()).start();
		}
		System.out.println("Wait...");
		barr.await(false, 0L); // threadNum个线程，则threadNum个await加上主线程的1个await，则可实现主线程和子线程同时执行
		System.out.println("End..");
	}
}
