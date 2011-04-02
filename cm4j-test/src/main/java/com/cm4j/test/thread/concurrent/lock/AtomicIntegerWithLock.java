package com.cm4j.test.thread.concurrent.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 * 采用lock机制实现类似AtomicInteger功能
 * 对于任何一个lock()方法，都需要一个unlock()方法与之对于，
 * 通常情况下为了保证unlock方法总是能够得到执行，unlock方法被置于finally块中
 * </pre>
 * 
 * @author yanghao
 * 
 */
public class AtomicIntegerWithLock {

    private int value;

    private Lock lock = new ReentrantLock();

    public AtomicIntegerWithLock() {
        super();
    }

    public AtomicIntegerWithLock(int value) {
        this.value = value;
    }

    public final int get() {
        lock.lock();
        try {
            return value;
        } finally {
            lock.unlock();
        }
    }

    public final void set(int newValue) {
        lock.lock();
        try {
            value = newValue;
        } finally {
            lock.unlock();
        }

    }

    public final int getAndSet(int newValue) {
        lock.lock();
        try {
            int ret = value;
            value = newValue;
            return ret;
        } finally {
            lock.unlock();
        }
    }

    public final boolean compareAndSet(int expect, int update) {
        lock.lock();
        try {
            if (value == expect) {
                value = update;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public final int getAndIncrement() {
        lock.lock();
        try {
            return value++;
        } finally {
            lock.unlock();
        }
    }

    public final int getAndDecrement() {
        lock.lock();
        try {
            return value--;
        } finally {
            lock.unlock();
        }
    }

    public final int incrementAndGet() {
        lock.lock();
        try {
            return ++value;
        } finally {
            lock.unlock();
        }
    }

    public final int decrementAndGet() {
        lock.lock();
        try {
            return --value;
        } finally {
            lock.unlock();
        }
    }

    public String toString() {
        return Integer.toString(get());
    }
    
    int staticValue = 0;
    public void plusValue () {
        ++ staticValue;
    }
    
    /**
     * <pre>
     * 每次启动10个线程，每个线程计算100000次自增操作，重复测试10次
     * Lock的性能比synchronized的要好得多。
     * 如果可以的话总是使用Lock替代synchronized是一个明智的选择
     * </pre>
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        final int max = 10;
        final int loopCount = 100000;
        long costTime = 0;
        for (int m = 0; m < max; m++) { // 重复测试10次
            long start1 = System.nanoTime();
            final AtomicIntegerWithLock value1 = new AtomicIntegerWithLock(0);
            Thread[] ts = new Thread[max];
            for(int i=0;i<max;i++) { // 每次启动10个线程
                ts[i] = new Thread() {
                    public void run() {
                        for (int i = 0; i < loopCount; i++) { //每个线程计算100000次自增操作
                            value1.incrementAndGet();
                        }
                    }
                };
            }
            for(Thread t:ts) {
                t.start();
            }
            for(Thread t:ts) {
                t.join();
            }
            long end1 = System.nanoTime();
            costTime += (end1-start1);
        }
        System.out.println("cost1: " + (costTime));
        //
        System.out.println();
        costTime = 0;
        //
        final Object lock = new Object();
        
        final AtomicIntegerWithLock atomicIntegerWithLock = new AtomicIntegerWithLock();
        for (int m = 0; m < max; m++) {
            long start1 = System.nanoTime();
            Thread[] ts = new Thread[max];
            for(int i=0;i<max;i++) {
                ts[i] = new Thread() {
                    public void run() {
                        for (int i = 0; i < loopCount; i++) {
                            synchronized(lock) {
                                atomicIntegerWithLock.plusValue();
                            }
                        }
                    }
                };
            }
            for(Thread t:ts) {
                t.start();
            }
            for(Thread t:ts) {
                t.join();
            }
            long end1 = System.nanoTime();
            costTime += (end1-start1);
        }
        //
        System.out.println("cost2: " + (costTime));
   }
}
