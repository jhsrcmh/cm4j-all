package com.cm4j.test.thread.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 测试类
 * 结果无意义，用于测试使用
 * @author yang.hao
 * @since 2012-1-30 下午1:34:10
 */
public class AtomicReferenceFieldUpdaterDemo {

	static class Demo {
		public volatile Long f1 = 0L;
		public  Long f2 = 0L;
		volatile String str = "abc";
	}

	static AtomicReferenceFieldUpdater<Demo, Long> getUpdater(String fieldName) {
		return AtomicReferenceFieldUpdater.newUpdater(Demo.class, Long.class, fieldName);
	}

	public static class R implements Runnable {
		private String name;
		private Demo d;

		public R(String name, Demo d) {
			this.name = name;
			this.d = d;
		}

		@Override
		public void run() {
			// 多线程循环调用
//			AtomicReferenceFieldUpdater<Demo, Long> updater = getUpdater("f1");
//			for (int i = 0; i < 1000; i++) {
//				updater.set(d, updater.get(d) + 1);
//				System.out.println(name + " ==> " + updater.get(d));
//			}
			for (int i = 0; i < 1000; i++) {
				d.f2 ++ ;
				System.out.println(name + " ==> " + d.f2);
			}
		}

	}

	public static void main(String[] args) {
		Demo d = new Demo();
		new Thread(new R("r1", d)).start();
		new Thread(new R("r2", d)).start();
	}

}
