package com.cm4j.test.thread.concurrent.counter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * 第一个闭锁确保在所有线程开始执行任务前，所有准备工作都已经完成，一旦准备工作完成了就调用startLatch.countDown()打开闭锁，
 * 所有线程开始执行。第二个闭锁在于确保所有任务执行完成后主线程才能继续进行，这样保证了主线程等待所有任务线程执行完成后才能得到需要的结果。
 * 在第二个闭锁当中，初始化了一个N次的计数器，每个任务执行完成后都会将计数器减一，所有任务完成后计数器就变为了0，
 * 这样主线程闭锁overLatch拿到此信号后就可以继续往下执行了
 * </pre>
 * 
 * @author yanghao
 * 
 */
public class CountDownLatchTest3 {
    /**
     * 计算task线程执行times次所需时间
     * 
     * @param times
     *            执行次数
     * @param task
     *            线程
     * @return
     * @throws InterruptedException
     */
    public long timecost(final int times, final Runnable task) throws InterruptedException {
        if (times <= 0)
            throw new IllegalArgumentException();
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch overLatch = new CountDownLatch(times);
        for (int i = 0; i < times; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        startLatch.await(1000, TimeUnit.MILLISECONDS);
                        //
                        task.run();
                    } catch (InterruptedException ex) {
                        System.out.println("thread interrupted");
                        Thread.currentThread().interrupt();
                    } finally {
                        overLatch.countDown();
                    }
                }
            }).start();
        }
        //
        long start = System.currentTimeMillis();
        startLatch.countDown();
        overLatch.await();
        return System.currentTimeMillis() - start;
    }

    public static void main(String[] args) throws InterruptedException {
        long result = 0L;
        result = new CountDownLatchTest3().timecost(10, new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("task execute");
            }
        });
        System.out.println(result);
    }
}
