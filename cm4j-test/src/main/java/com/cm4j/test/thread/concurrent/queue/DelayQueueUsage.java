package com.cm4j.test.thread.concurrent.queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延时队列的使用
 * 
 * @author yang.hao
 * @since 2011-9-1 上午9:26:52
 */
public class DelayQueueUsage {
	/**
	 * 注意对TimeUnit的使用
	 * 
	 * @author yang.hao
	 * @since 2011-9-1 上午9:27:03
	 */
	class DelayItem<E> implements Delayed {
		private final long create = System.nanoTime();

		/**
		 * 当前时间
		 */
		private long now() {
			return System.nanoTime();
		}

		/**
		 * 对象存活时间
		 */
		private long aliveTime() {
			return now() - create;
		}

		/**
		 * 最大存活时间
		 */
		private long maxAliveTime;
		private E leader;

		public DelayItem(E e, long maxAliveTime, TimeUnit unit) {
			this.leader = e;
			this.maxAliveTime = TimeUnit.NANOSECONDS.convert(maxAliveTime, unit);
		}

		public E getLeader() {
			return this.leader;
		}

		/**
		 * 排序规则对象优先级，返回正数、0、负数<br>
		 * 参考JDK中其他Comparable实现
		 */
		@Override
		public int compareTo(Delayed other) {
			if (this == other) {
				return 0;
			}
			// 两者剩余时间相减
			long diff = getDelay(TimeUnit.MILLISECONDS) - other.getDelay(TimeUnit.MILLISECONDS);
			return (diff == 0 ? 0 : ((diff < 0) ? -1 : 1));
		}

		/**
		 * 返回这个对象的剩余延时时间，剩余延时时间为0，则表示可立即获得此对象
		 */
		@Override
		public long getDelay(TimeUnit unit) {
			return unit.convert(maxAliveTime - aliveTime(), TimeUnit.NANOSECONDS);
		}
	}

	private DelayQueue<DelayItem<Object>> q = new DelayQueue<DelayQueueUsage.DelayItem<Object>>();

	public void put(Object object) {
		DelayItem<Object> e = new DelayItem<Object>(object, 3, TimeUnit.SECONDS);
		q.offer(e);
	}

	public Object get() {
		DelayItem<Object> delayItem = q.poll();
		return delayItem == null ? null : delayItem.getLeader();
	}

	public static void main(String[] args) throws InterruptedException {
		DelayQueueUsage usage = new DelayQueueUsage();
		usage.put("a");

		// 立即获取，获取不到
		System.out.println(usage.get());
		// 等三秒，获取到
		Thread.sleep(3100);
		System.out.println(usage.get());
	}
}
