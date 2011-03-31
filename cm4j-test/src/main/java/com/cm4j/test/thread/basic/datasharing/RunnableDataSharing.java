package com.cm4j.test.thread.basic.datasharing;

/**
 * 实现资源共享
 * 
 * @author yanghao
 *
 */
public class RunnableDataSharing implements Runnable{

    private int ticket = 10;

    @Override
    public void run() {
        while (true) {
            if (this.ticket > 0) {
                System.out.println("卖票：ticket" + this.ticket--);
            }
        }
    }
    
    public static void main(String[] args) {
        RunnableDataSharing dataSharing = new RunnableDataSharing();
        Thread mt1 = new Thread(dataSharing);
        Thread mt2 = new Thread(dataSharing);
        Thread mt3 = new Thread(dataSharing);
        mt1.start();// 同一个dataSharing，但是在Thread中就不可以，如果用同一个实例化对象mt，就会出现异常
        mt2.start();
        mt3.start();// 达到资源共享
    }

}
