package com.cm4j.test.thread.concurrent.atomic;

import sun.misc.Unsafe;

/**
 * 仅作代码演示，无法运行
 * 因为Unsafe类被JDK禁止访问，所以运行报错
 * 
 * @author yang.hao
 * @since 2012-1-30 下午1:56:09
 */
@SuppressWarnings("restriction")
public class AtomicIntegerTest {

	private static final Unsafe unsafe = Unsafe.getUnsafe();
	private static final long valueOffset;

	static {
		try {
			valueOffset = unsafe.objectFieldOffset(Atomic.class.getDeclaredField("value"));
		} catch (Exception ex) {
			throw new Error(ex);
		}
	}

	static class Atomic {
		private volatile int value;

		public Atomic(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public int increaseAndGet() {
			for (;;) {
				int current = getValue();
				int next = current + 1;
				if (unsafe.compareAndSwapInt(this, valueOffset, current, next)) {
					return next;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Atomic atomic = new Atomic(1);
		System.out.println(atomic.increaseAndGet());
	}
}
