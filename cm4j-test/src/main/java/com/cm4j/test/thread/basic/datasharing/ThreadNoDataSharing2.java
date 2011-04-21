package com.cm4j.test.thread.basic.datasharing;

/**
 * 未达到资源共享
 * 
 * @author yanghao
 * 
 */
public class ThreadNoDataSharing2 extends Thread {

	private int ticket = 10;

	public void run() {
		int m = ticket;
		Thread.yield();
		m++;
		ticket = m;
		System.out.println(ticket);
	}

	public static void main(String[] args) {
		ThreadNoDataSharing2 dataSharing = new ThreadNoDataSharing2();
		Thread mt1 = new Thread(dataSharing);
		Thread mt2 = new Thread(dataSharing);
		Thread mt3 = new Thread(dataSharing);
		mt1.start();// 同一个dataSharing，但是在Thread中就不可以，如果用同一个实例化对象mt，就会出现异常
		mt2.start();
		mt3.start();// 达到资源共享
	}
}
