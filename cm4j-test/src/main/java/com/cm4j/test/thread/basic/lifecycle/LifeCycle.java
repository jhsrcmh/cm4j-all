package com.cm4j.test.thread.basic.lifecycle;

public class LifeCycle extends Thread {

    @Override
    public void run() {
        
        try {
            // sleep使线程休眠后，只能在设定的时间后使线程处于就绪状态
            // （在线程休眠结束后，线程不一定会马上执行，只是进入了就绪状态，等待着系统进行调度）
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LifeCycle thread1 = new LifeCycle();
        System.out.println("isAlive: " + thread1.isAlive());
        thread1.start();
        System.out.println("isAlive: " + thread1.isAlive());
        thread1.join(); // 等线程thread1结束后再继续执行
        System.out.println("thread1已经结束!");
        System.out.println("isAlive: " + thread1.isAlive());
    }
}
