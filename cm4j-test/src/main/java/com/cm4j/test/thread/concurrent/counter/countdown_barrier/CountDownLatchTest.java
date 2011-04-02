package com.cm4j.test.thread.concurrent.counter.countdown_barrier;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * <pre>
 * 在多线程设计中，我猜常常会遇到线程间相互等待以及某个线程等待1个或多个线程的场景，比如多线程精密计算和大量数据处理。
 * 
 * 我想应该有很多办法，如果是简单的1：1关系，那么可以wait（）和notify（）解决，就像一把锁和一把钥匙；
 * 如果是1：N关系，这个1就需要关心N的所有状态了，最笨的办法是1可以去查看N当前的状态，轮询询问工作是否做完。
 * <font color=red>而好点的办法是N做完后主动告诉1(await()方法)，
 * 然后N就会有2种选择：
 * 要么听从1的命令，等待其他线程完毕后一起执行下面的操作(CyclicBarrier)，
 * 要么继续干自己其他的活(CountDownLatch)。
 * 
 * 用传统的方法我想应该是都能实现的，而JDK1.5提供了CyclicBarrier与CountDownLatch来解决了这两个问题，而她们的区别是：
 * CyclicBarrier使所有线程相互等待，而CountDownLatch使一个或多个线程等待其他线程。</font>
 * 区别类似上面蓝色字体，CountDownLatch不会等待其他线程了，只要做完自己的工作就干自己的活去了，也就是run()方法里其他的任务。
 * </pre>
 * 
 * <font color=red>调用countdown注意是哪个线程等待哪个线程</font>
 * @author yanghao
 * 
 */
public class CountDownLatchTest {
    private static final Logger logger = LoggerFactory.getLogger(CountDownLatchTest.class);

    public static void testCountDownLatch() throws InterruptedException {

        CountDownLatch startCountDown = new CountDownLatch(1);
        CountDownLatch endCountDown = new CountDownLatch(2);
        ExecutorService exe = Executors.newFixedThreadPool(2);
        class Bow implements Runnable {
            CountDownLatch startCountDown, endCountDown;

            public Bow(CountDownLatch startCountDown, CountDownLatch endCountDown) {
                this.startCountDown = startCountDown;
                this.endCountDown = endCountDown;
            }

            public void run() {
                try {
                    // 等待startCountDown - countdown()完成后再向下执行，不是互相等待
                    this.startCountDown.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 继续执行do other thing，只是通知，并不等待其他线程
                this.endCountDown.countDown();
            }
        }

        for (int i = 0; i < 2; i++) {
            exe.execute(new Bow(startCountDown, endCountDown));
        }
        exe.shutdown();

        // 这里类似于初始化完成，初始化完成，等待的线程就可以继续执行，
        startCountDown.countDown();

        // 等待endCountDown所有线程都执行完endCountDown
        endCountDown.await();
    }

    public static void main(String[] args) {
        try {
            CountDownLatchTest.testCountDownLatch();
        } catch (InterruptedException e) {
        }
    }
}
