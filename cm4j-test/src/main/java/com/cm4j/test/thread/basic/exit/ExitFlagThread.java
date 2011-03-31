package com.cm4j.test.thread.basic.exit;

/**
 * <pre>
 * 终止线程 - 本类为第一种：退出标志
 * 1.  使用退出标志，使线程正常退出，也就是当run方法完成后线程终止。
 * 2.  使用stop方法强行终止线程（这个方法不推荐使用，因为stop和suspend、resume一样，也可能发生不可预料的结果）。
 * 3.  使用interrupt方法中断线程。
 * </pre>
 * 
 * @author yanghao
 *
 */
public class ExitFlagThread extends Thread{

    // 目的是使exit同步，也就是说在同一时刻只能由一个线程来修改exit的值
    public volatile boolean exit = false;
    
    @Override
    public void run() {
        int i = 0;
        while (!exit) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i ++ ;
            System.out.println("thread sleep " + 1000 * i);
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        ExitFlagThread thread = new ExitFlagThread();
        thread.start(); // thread线程开始计数
        sleep(5000); // 主线程延迟5秒
        thread.exit = true; //thread线程停止
        thread.join();
        System.out.println("thread退出");
    }
}
