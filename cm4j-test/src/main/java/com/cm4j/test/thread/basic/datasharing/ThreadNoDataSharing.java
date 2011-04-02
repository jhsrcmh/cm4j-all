package com.cm4j.test.thread.basic.datasharing;

/**
 * 未达到资源共享
 * 
 * @author yanghao
 * 
 */
public class ThreadNoDataSharing extends Thread {

    private int ticket = 10;

    public void run() {
        while(true) {
            if (this.ticket > 0) {
                System.out.println("卖票：ticket" + this.ticket--);
            }
        }
    }

    public static void main(String[] args) {
        Thread mt1 = new ThreadNoDataSharing();
        Thread mt2 = new ThreadNoDataSharing();
        Thread mt3 = new ThreadNoDataSharing();
        mt1.start();// 每个线程都各卖了10张，共卖了30张票
        mt2.start();// 但实际只有10张票，每个线程都卖自己的票
        mt3.start();// 没有达到资源共享
    }
}
