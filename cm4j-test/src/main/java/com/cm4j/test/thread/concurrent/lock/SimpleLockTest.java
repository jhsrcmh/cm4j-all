package com.cm4j.test.thread.concurrent.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleLockTest {

	int idx = 0;
	Lock lock = new ReentrantLock();
	
	public int increase() {
		try {
			lock.lock();
			int result = idx + 1;
			Thread.yield();
			idx = result;
			return result;			
		} finally {
			lock.unlock();
		}
	}

	void t() {
		new Thread(new R(this, "t1")).start();
		new Thread(new R(this, "t2")).start();
		new Thread(new R(this, "t3")).start();
	}

	public static void main(String[] args) {
		SimpleLockTest test = new SimpleLockTest();
		test.t();
	}
}

class R implements Runnable {
	private String name;
	private SimpleLockTest t;

	public R(SimpleLockTest t, String name) {
		this.t = t;
		this.name = name;
	}

	@Override
	public void run() {
		for (int i = 0; i < 5000; i++) {
				// 加锁的意义在于多线程获取同一个锁，这样每个线程就会按照获取锁的顺序执行
				// 要防止每个线程都创建一个锁的情况

				// 这里也可以不加锁，而把锁的定义放在SimpleLockTest类里，而锁加在increase()里面，
				// 这样多个线程也是获取的同一个锁
				System.out.println(name + "=>" + t.increase());
		}
	}
}