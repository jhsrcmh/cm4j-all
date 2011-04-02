package com.cm4j.test.thread.basic.sync.syncthread;

/**
 * <pre>
 * 变量同步：实质：利用类变量来获得同步锁，通过同步锁的互斥性来实现同步 - 有问题 
 * 
 * 问题：被同步变量是static，使用method()的方法应该占用对象锁，其他线程再调用method应该被阻塞
 * 但是本类2个线程都打印语句了
 * 
 * 原因：同步块内第一行：sync = s; 这里已经将string对象s的值更改，创建了一个新的string实例，
 * 所以第一个线程的string对象的锁和第二个线程占用的string对象的锁不是同一个
 * 
 * 引用如下：
 * 由于在012行已经为sync创建了一个新的实例，假设method1先执行，当method1方法执行了013行的代码后，
 * sync的值就已经不是最初那个值了，而method1方法锁定的仍然是sync变量最初的那个值。
 * 而在这时，staticMethod1正好执行到synchronized(sync)，在staticMethod1方法中要锁定的这个sync和method1
 * 方法锁定的sync已经不是一个了，因此，这两个方法的同步性已经被破坏了。
 * 
 * 解决方法：禁止同步变量被修改，在同步变量定义时加修饰符final
 * 2.去除sync=s；
 * </pre>
 * 
 * @author yanghao
 * 
 */
public class SyncThread extends Thread {
    private static String sync = ""; // 为避免出现同步变量被修改，最好是在前面加final修饰符

    private String methodType = "";

    private static void method(String s) {
        synchronized (sync) {
            sync = s;
            System.out.println(s);
            while (true)
                ;
        }
    }

    public void method1() {
        method("method1");
    }

    public static void staticMethod1() {
        method("staticMethod1");
    }

    public void run() {
        if (methodType.equals("static"))
            staticMethod1();
        else if (methodType.equals("nonstatic"))
            method1();
    }

    public SyncThread(String methodType) {
        this.methodType = methodType;
    }

    public static void main(String[] args) throws Exception {
        SyncThread sample1 = new SyncThread("nonstatic");
        SyncThread sample2 = new SyncThread("static");
        sample1.start();
        sample2.start();
    }
}