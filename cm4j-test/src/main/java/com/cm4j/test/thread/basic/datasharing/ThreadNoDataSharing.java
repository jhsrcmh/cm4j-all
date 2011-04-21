package com.cm4j.test.thread.basic.datasharing;

/**
 * 未达到资源共享
 * 
 * @author yanghao
 * 
 */
public class ThreadNoDataSharing extends Thread {
	public int n = 0;

	@Override
	public void run() {
		int m = n;
		yield();
		m++;
		n = m;
	}

	public int getN() {
		return n;
	}

	public static void main(String[] args) throws Exception {
		ThreadNoDataSharing myThread = new ThreadNoDataSharing();
		Thread threads[] = new Thread[100];
		for (int i = 0; i < threads.length; i++)
			threads[i] = new Thread(myThread);
		for (int i = 0; i < threads.length; i++)
			threads[i].start();
		for (int i = 0; i < threads.length; i++)
			threads[i].join();
		System.out.println("n = " + myThread.getN());
	}
}
