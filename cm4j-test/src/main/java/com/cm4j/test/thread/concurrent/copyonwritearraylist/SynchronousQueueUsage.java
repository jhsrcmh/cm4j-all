package com.cm4j.test.thread.concurrent.copyonwritearraylist;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 生产者
 */
class Producer implements Runnable {
    private BlockingQueue<String> drop;

    List<String> messages = Arrays.asList("Mares eat oats", "Does eat oats", "Little lambs eat ivy",
            "Wouldn't you eat ivy too?");

    public Producer(BlockingQueue<String> d) {
        this.drop = d;
    }

    public void run() {
        try {
            for (String s : messages)
                drop.put(s);
            drop.put("DONE");
        } catch (InterruptedException intEx) {
            System.out.println("Interrupted! " + "Last one out, turn out the lights!");
        }
    }
}

/**
 * 消费者
 */
class Consumer implements Runnable {
    private BlockingQueue<String> drop;

    public Consumer(BlockingQueue<String> d) {
        this.drop = d;
    }

    public void run() {
        try {
            Thread.sleep(3000);
            String msg = null;
            // 当取到的不是DONE，则打印且继续循环，否则退出
            while (!((msg = drop.take()).equals("DONE")))
                System.out.println(msg);
        } catch (InterruptedException intEx) {
            System.out.println("Interrupted! " + "Last one out, turn out the lights!");
        }
    }
}

public class SynchronousQueueUsage {
    public static void main(String[] args) {
        BlockingQueue<String> drop = new SynchronousQueue<String>();
        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer(drop))).start();
    }
}