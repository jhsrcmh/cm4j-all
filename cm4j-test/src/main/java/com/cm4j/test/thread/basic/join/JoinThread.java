package com.cm4j.test.thread.basic.join;

/**
 * join作用 - join方法的功能就是使异步执行的线程变成同步执行
 * JoinThread 功能 - 对JoinThread的静态变量c进行累加
 * @author yanghao
 *
 */
public class JoinThread extends Thread {

    public static int c = 0;

    static synchronized void inc() {
        c++;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            inc();
            try {
                sleep(3); // 为了使运行结果更随机，延迟3毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("thread is done !");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread threads[] = new Thread[100];
        for (int i = 0; i < threads.length; i++)
            // 建立100个线程
            threads[i] = new JoinThread();
        for (int i = 0; i < threads.length; i++) {
            // 运行刚才建立的100个线程
            threads[i].start();
            // 如果不加这句，各线程并发执行，结果不为1000，各线程还没执行完就已经打印语句
            // 如果加了这句，则各线程同步运行，各线程执行完才会打印语句，结果肯定为1000
            threads[i].join();
        }
        System.out.println("c=" + JoinThread.c);
    }
}
