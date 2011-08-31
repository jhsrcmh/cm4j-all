package com.cm4j.test.thread.concurrent.queue;

import java.util.concurrent.SynchronousQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynchronousQueueUsage {
	public static final Logger logger = LoggerFactory.getLogger(BlockingQueueUsage2.class);

	public class Basket {
		private SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();

		public void put(Integer apple) throws InterruptedException {
			Thread.sleep(250);
			queue.put(apple);
		}

		public Integer get() throws InterruptedException {
			return queue.take();
		}

		class PutThread extends Thread {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					try {
						queue.put(i);
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		class GetThread extends Thread {
			@Override
			public void run() {
				for (;;) {
					try {
						int result = queue.take();
						logger.debug("获取值：{}", result);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		SynchronousQueueUsage usage = new SynchronousQueueUsage();
		Basket basket = usage.new Basket();
		basket.new PutThread().start();
		basket.new GetThread().start();
	}
}
