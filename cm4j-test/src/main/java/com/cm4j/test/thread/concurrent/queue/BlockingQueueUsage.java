package com.cm4j.test.thread.concurrent.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * BlockingQueue 的使用 - 放、取物品
 * 
 * @author yanghao
 * 
 */
public class BlockingQueueUsage {

    class PutThread extends Thread {
        private BlockingQueue<Object> blockingQueue;

        public PutThread(BlockingQueue<Object> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            int i = 0;
            do {
                i++;
                try {
                    Object obj = "obj:" + i;
                    blockingQueue.put(obj);
                    System.out.println("blockingQueue put:" + obj);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (i < 10);
        }
    }

    class TakeThread extends Thread {

        private BlockingQueue<Object> blockingQueue;

        public TakeThread(BlockingQueue<Object> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Object obj = blockingQueue.take();
                    System.out.println(obj);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        final BlockingQueue<Object> blockingQueue = new ArrayBlockingQueue<Object>(5);

        BlockingQueueUsage usage = new BlockingQueueUsage();
        usage.new PutThread(blockingQueue).start();
        usage.new TakeThread(blockingQueue).start();
    }
}
