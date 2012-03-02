package com.cm4j.test.thread.concurrent.coordination.countdown_barrier;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 自实现 模拟简化版的CountdownLatch的 功能
 * 
 * @author yang.hao
 * @since 2012-2-22 下午2:47:22
 */
public class Self_CountdownLatchTest {

	private int limit;
	private Sync sync;

	public Self_CountdownLatchTest() {
		super();
	}

	public Self_CountdownLatchTest(int limit) {
		this.limit = limit;
		sync = new Sync(this.limit);
	}

	class Sync extends AbstractQueuedSynchronizer {
		private static final long serialVersionUID = 1L;

		Sync(int limit) {
			super.setState(limit);
		}

		@Override
		protected int tryAcquireShared(int arg) {
			return getState() == 0 ? 1 : -1;
		}

		@Override
		protected boolean tryReleaseShared(int arg) {
			int state = getState();
			int next = state - 1;
			for (;;) {
				if (compareAndSetState(state, next)) {
					return true;
				}
			}
		}
	}

	public void countDown() {
		sync.releaseShared(1);
	}

	public void await() {
		sync.acquireShared(1);
	}

	public static void main(String[] args) {
		final int count = 3;
		final Self_CountdownLatchTest completeLatch = new Self_CountdownLatchTest(count);
		for (int i = 0; i < count; i++) {
			new Thread("thread:" + i) {
				public void run() {
					System.out.println(this.getName() + " init done...");
					completeLatch.countDown();
				};
			}.start();
		}

		// 保证所有线程在同一重点开始跑
		completeLatch.await(); // main线程 等 N线程

		System.out.println("all threads done,main thread to go");
	}

}
