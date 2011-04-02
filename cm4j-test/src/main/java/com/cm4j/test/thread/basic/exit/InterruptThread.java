package com.cm4j.test.thread.basic.exit;

/**
 * 中断线程
 * 
 * @author yanghao
 *
 */
public class InterruptThread extends Thread{

    @Override
    public void run() {
        try {
            sleep(50000);
        } catch (InterruptedException e) {
            System.out.println("thread is interrupted");
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws Exception {
        Thread thread = new InterruptThread();
        thread.start();
        System.out.println("在50秒之内按任意键中断线程!");
        System.in.read();
        thread.interrupt();
        thread.join();
        System.out.println("线程已退出");
    }
}
