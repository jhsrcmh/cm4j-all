package com.cm4j.test.thread.concurrent.util;

import java.util.ArrayList;
import java.util.concurrent.Exchanger;

/**
 * <pre>
 * 描述如下： 
 * 1.Exchanger用于在2个线程中交换对象。 
 * 2.return_object = exchanger.exchange(exch_object) 
 * 3.例子中producer向ArrayList中缓慢填充随机整数，consumer从另一个ArrayList中缓慢取出整数并输出。 
 * 4.当producer的ArrayList填满，并且consumer的ArrayList为空时，2个线程才交换ArrayList。
 * </pre>
 * 
 * @author yang.hao
 * @since 2011-8-26 上午11:56:52
 */
public class ExchangerTest {

	public static void main(String[] args) {
		final Exchanger<ArrayList<Integer>> exchanger = new Exchanger<ArrayList<Integer>>();

		new Thread(new Runnable() {
			@Override
			public void run() {
				ArrayList<Integer> buff = new ArrayList<Integer>(10);
				try {
					while (true) {
						// 当buf大小大于等于10，才和另一个线程交换数据
						if (buff.size() >= 10) {
							// 等待另一个线程也执行到exchange()方法，开始跟另外一个线程交换数据
							buff = exchanger.exchange(buff);
							System.out.println("exchange buff1");
							buff.clear();
						}

						// 添加数据，达到10个，交换数据
						buff.add((int) (Math.random() * 100));
						Thread.sleep(500);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "producer").start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				ArrayList<Integer> buff = new ArrayList<Integer>(10);
				while (true) {
					try {
						for (Integer i : buff) {
							System.out.println("from thread1 ==> " + i);
						}
						buff.clear();
						// 等待另一个线程也执行到exchange()方法，开始跟另外一个线程交换数据
						// 如果另一个线程没执行到exchange()，则暂挂当前线程等待
						if (buff.isEmpty()){
							buff = exchanger.exchange(buff);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "consumer").start();
	}
}
