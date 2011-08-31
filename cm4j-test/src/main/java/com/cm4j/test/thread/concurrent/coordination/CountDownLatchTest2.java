package com.cm4j.test.thread.concurrent.coordination;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 可用于程序初始化
 * 
 * @author yanghao
 *
 */
public class CountDownLatchTest2 {

    public static void main(String[] args) {
        System.out.println("Server is starting.");

        // 初始化一个初始值为3的CountDownLatch
        CountDownLatch latch = new CountDownLatch(3);

        // 起3个线程分别去启动3个组件
        ExecutorService service = Executors.newCachedThreadPool();

        service.submit(new ComponentThread(latch, 1));
        service.submit(new ComponentThread(latch, 2));
        service.submit(new ComponentThread(latch, 3));
        service.shutdown();

        boolean awaitResult = false;
        // 进入等待状态
        try {
            awaitResult = latch.await(10000,TimeUnit.MILLISECONDS);
            System.out.println("awaitResult:" + awaitResult);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 当所需的三个组件都完成时，Server就可继续了
        System.out.println("Server is up!");
    }
}

class ComponentThread implements Runnable {

    CountDownLatch latch;

    int ID;

    /** Creates a new instance of ComponentThread */
    public ComponentThread(CountDownLatch latch, int ID) {
        this.latch = latch;
        this.ID = ID;
    }

    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Component " + ID + " initialized!");
        // 将计数减一
        latch.countDown();
        System.out.println("haha");
    }

}
