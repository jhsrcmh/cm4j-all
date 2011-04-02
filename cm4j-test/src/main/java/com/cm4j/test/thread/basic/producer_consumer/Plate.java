package com.cm4j.test.thread.basic.producer_consumer;

import java.util.List;
import java.util.ArrayList;

/**
 * <pre>
 * 1 开始，A调用plate.putEgg方法，此时eggs.size()为0，因此顺利将鸡蛋放到盘子，
 *      还执行了notify()方法，唤醒锁的阻塞队列 的线程，此时阻塞队列还没有线程。
 * 2 又有一个A线程对象调用plate.putEgg方法，此时eggs.size()不为0，
 *      调用wait()方法，自己进入了锁对象的阻塞队列。
 * 3 此时，来了一个B线程对象，调用plate.getEgg方法，eggs.size()不为0，
 *      顺利的拿到了一个鸡蛋，还执行了notify()方法，唤 醒锁的阻塞队列的线程，
 *      此时阻塞队列有一个A线程对象，唤醒后，它进入到就绪队列，就绪队列也就它一个，因此马上得到锁，开始往盘子里放鸡蛋，
 *      此时盘子是 空的，因此放鸡蛋成功。
 * 4 假设接着来了线程A，就重复2；假设来料线程B，就重复3。 
 * 整个过程都保证了放鸡蛋，拿鸡蛋，放鸡蛋，拿鸡蛋。
 * </pre>
 * 
 * @author yanghao
 * 
 */
public class Plate {

    List<Object> eggs = new ArrayList<Object>();

    public synchronized Object getEgg() {
        if (eggs.size() == 0) {
            try {
                // 让线程主动释放锁，进入阻塞队列
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Object egg = eggs.get(0);
        // 打印语句放在同步方法内，保证打印顺序
        // 如果放在线程的for循环中，因为线程for循环不同步，打印顺序就会乱
        System.out.println("get:" + egg);
        eggs.clear();
        notify(); // 唤醒阻塞队列的某线程到就绪队列
        return egg;
    }

    public synchronized void putEgg(Object egg) {
        if (eggs.size() > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        eggs.add(egg);
        System.out.println("put:" + egg);
        notify();
    }

    public class PutThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                putEgg(i);
            }
        }
    }

    public class GetThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                Object egg = getEgg();
            }
        }
    }

    public static void main(String[] args) {
        Plate plate = new Plate();
        Thread putThread = plate.new PutThread();
        Thread getThread = plate.new GetThread();
        putThread.start();
        getThread.start();
    }
}
