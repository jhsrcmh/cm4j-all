package com.cm4j.test.thread.concurrent.counter.countdown_barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 和CountDownLatch区别请看{@link CountDownLatchTest}
 * 用法：threadNum个线程，则threadNum个await加上主线程的1个await，则可实现主线程和子线程同时执行
 * @author yanghao
 *
 */
public class CyclicBarrierUsage {
    public static void testCyclicBarrier() throws InterruptedException, BrokenBarrierException {
        int threadNum = 2;
        CyclicBarrier barr = new CyclicBarrier(threadNum + 1);

        ExecutorService exe = Executors.newFixedThreadPool(10);
        class Bow implements Runnable {
            CyclicBarrier barr;

            public Bow(CyclicBarrier barr) {
                this.barr = barr;
            }

            public void run() {
                System.out.println("The bow is coming");
                System.out.println("kick a down");
                try {
                    barr.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("do other thing");
            }
        }
        for (int i = 0; i < threadNum; i++) {
            exe.execute(new Bow(barr));
        }
        exe.shutdown();
        System.out.println("Wait...");
        barr.await(); // threadNum个线程，则threadNum个await加上主线程的1个await，则可实现主线程和子线程同时执行
        System.out.println("End..");

    }

    public static void main(String[] args) {
        try {
            CyclicBarrierUsage.testCyclicBarrier();
        } catch (Exception e) {
        } 
    }
}
