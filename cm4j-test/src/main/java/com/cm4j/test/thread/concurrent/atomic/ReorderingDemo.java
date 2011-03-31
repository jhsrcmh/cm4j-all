package com.cm4j.test.thread.concurrent.atomic;

/**
 * 指令重排序：只要程序的最终结果等同于它在严格的顺序化环境下的结果，那么指令的执行顺序就可能与代码的顺序不一致。
 * 存在的意义：JVM能够根据处理器的特性（CPU的多级缓存系统、多核处理器等）适当的重新排序机器指令，
 * 使机器指令更符合CPU的执行特点，最大限度的发挥机器的性能。
 * 
 * @author yanghao
 *
 */
public class ReorderingDemo {

    static int x = 0, y = 0, a = 0, b = 0;

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 1000; i++) {
            x = y = a = b = 0;
            Thread one = new Thread() {
                public void run() {
                    a = 1;
                    x = b;
                }
            };
            Thread two = new Thread() {
                public void run() {
                    b = 1;
                    y = a;
                }
            };
            one.start();
            two.start();
            one.join();
            two.join();
            System.out.println(x + " " + y);
        }
    }

}
