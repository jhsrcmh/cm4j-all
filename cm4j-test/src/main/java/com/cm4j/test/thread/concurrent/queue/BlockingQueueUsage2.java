package com.cm4j.test.thread.concurrent.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BlockingQueue 的使用 - 放、取物品
 * 
 * @author yanghao
 * 
 */
public class BlockingQueueUsage2 {

    public static final Logger logger = LoggerFactory.getLogger(BlockingQueueUsage2.class);

    public class Basket {
        // 篮子，能够容纳3个苹果
        private BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);

        public void put(Integer apple) throws InterruptedException {
            Thread.sleep(250);
            queue.put(apple);
        }

        public Integer get() throws InterruptedException {
            return queue.take();
        }
    }

    // 放 - 线程
    public class PutThread extends Thread {

        private Basket basket;

        public PutThread(Basket basket) {
            this.basket = basket;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    basket.put(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 取 - 线程
    public class GetThread extends Thread {
        private Basket basket;

        public GetThread(Basket basket) {
            this.basket = basket;
        }

        @Override
        public void run() {
            for (;;) {
                try {
                    int result = basket.get();
                    logger.debug("获取值：{}", result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        BlockingQueueUsage2 usage = new BlockingQueueUsage2();
        Basket basket = usage.new Basket();
        usage.new PutThread(basket).start();
        usage.new GetThread(basket).start();
    }
}
