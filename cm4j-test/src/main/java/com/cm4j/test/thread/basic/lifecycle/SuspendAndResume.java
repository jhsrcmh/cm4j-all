package com.cm4j.test.thread.basic.lifecycle;

/**
 * <pre>
 * 
 * sleep方法并不等同于suspend。
 * 它们之间最大的一个区别是可以在一个线程中通过suspend方法来挂起另外一个线程，如上面代码中在主线程中挂起了thread线程。
 * 而sleep只对当前正在执行的线程起作用
 * 
 * sleep使线程休眠后，只能在设定的时间后使线程处于就绪状态
 * （在线程休眠结束后，线程不一定会马上执行，只是进入了就绪状态，等待着系统进行调度）
 * 
 * stop和suspend、resume一样，也可能死锁倾向，故不推荐使用，后期jdk中可能会删除
 * </pre>
 * 
 * @author yanghao
 * 
 */
public class SuspendAndResume extends Thread {

    class SleepThread extends Thread {
        @Override
        public void run() {
            System.out.println("sleepthread sleep 2000");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println("sleepthread done");
        }
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(System.currentTimeMillis());
        }
    }

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws Exception {
        SuspendAndResume thread = new SuspendAndResume();
        SleepThread sleepThread = thread.new SleepThread();
        sleepThread.start(); // 开始运行线程sleepThread
        sleepThread.join(); // 使当前线程等待sleepThread执行完再继续执行，如果不加，则sleepThread和thread会同时执行
        thread.start();

        boolean flag = false;
        while (true) {
            sleep(5000);
            flag = !flag;
            if (flag) {
                System.out.println("main thread suspend");
                thread.suspend();
            } else {
                System.out.println("main thread resume");
                thread.resume();
            }
        }
    }
}
