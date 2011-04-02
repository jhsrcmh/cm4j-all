package com.cm4j.test.thread.concurrent.lock;

import java.util.concurrent.atomic.AtomicInteger;

public class LockFreeUsage {

    /**
     * 计数器
     * 若要线程安全，需要加锁
     */
    class LockCounter {
        private volatile int max = 0;

        public synchronized void set(int value) {
            if (value > max) {
                max = value;
            }
        }

        public int getMax() {
            return max;
        }
    }

    /**
     * LockFree 算法 - 不用加锁
     * 步骤：
     * 1.循环
     * 2.CAS
     * 3.回退
     */
    class LockFreeCounter {
        private AtomicInteger max = new AtomicInteger();

        /**
         * 计数器功能：set只对最大值进行增量，而不是减少！~
         * @param value
         */
        public void set(int value) {
            for (;;) {  // 步骤1：循环
                int current = max.get();
                if (value > current) {
                    if (max.compareAndSet(current, value)) { // 步骤2：CAS
                        break;  // 步骤3：回退
                    } else {
                        continue;
                    }
                } else {
                    break;
                }
            }
        }

        public int getMax() {
            return max.get();
        }
    }
    
    public static void main(String[] args) {
        LockFreeUsage usage = new LockFreeUsage();
        LockFreeCounter lockFreeCounter = usage.new LockFreeCounter();
        lockFreeCounter.set(10);
        System.out.println(lockFreeCounter.getMax());
        // 设置只增加而不减少！~ 因此这里不减少
        lockFreeCounter.set(5); 
        System.out.println(lockFreeCounter.getMax());
        lockFreeCounter.set(15);
        System.out.println(lockFreeCounter.getMax());
    }
}
