package com.cm4j.test.thread.basic.passparams;

import java.util.Random;

/**
 * 线程类 - 回调函数获取参数(在线程运行的过程中动态地获取数据)
 * @author yanghao
 *
 */
public class PassParamsThread extends Thread {
    private Work work;

    // A.将Work对象传到线程中
    public PassParamsThread(Work work) {
        this.work = work;
    }

    @Override
    public void run() {
        Random random = new Random();
        Data data = new Data();
        int nextInt = random.nextInt(1000);
        int nextInt2 = random.nextInt(1000);
        int nextInt3 = random.nextInt(1000);
        work.process(data, nextInt, nextInt2, nextInt3); // 使用回调函数,同时注意Integer...参数的用法
        System.out.println(String.valueOf(nextInt) + "+" + String.valueOf(nextInt2) + "+"
                + String.valueOf(nextInt3) + "=" + data.value);
    }
    
    public static void main(String[] args) {
        Thread thread = new PassParamsThread(new Work());
        thread.start();
    }
}

class Data {
    public int value = 0;
}

class Work {
    // 回调函数，从本质上说，回调函数就是事件函数
    public void process(Data data, Integer... numbers) {
        for (int i : numbers) {
            data.value += i;
        }
    }
}