package com.cm4j.test.thread.basic.sync;

public class ExecThread extends Thread {

    private Sun sun;

    public ExecThread(Sun sun) {
        this.sun = sun;
    }

    public void run() {
        sun.addInt();
    };
    
    public static void main(String[] args) throws InterruptedException {
        int max = 1000;
        Thread[] t = new Thread[max];
        Sun sun = new Sun();
        for (int i = 0; i < max; i++) 
            t[i] = new ExecThread(sun);
            
        for (int i = 0; i < max; i++) 
            t[i].start();
            
        sun.stdout();
    }
}
