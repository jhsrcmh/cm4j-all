package com.cm4j.test.thread.concurrent.counter.semaphore.simple_semaphore;

public class UseSemaphorerForSignaling {

    public class SendingThread extends Thread {
        MN_Semaphorer semaphorer;

        public SendingThread(MN_Semaphorer semaphorer) {
            super();
            this.semaphorer = semaphorer;
        }

        @Override
        public void run() {
            while (true) {
                // do something then signal
                try {
                    semaphorer.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class ReceivingThread extends Thread {
        MN_Semaphorer semaphorer;

        public ReceivingThread(MN_Semaphorer semaphorer) {
            this.semaphorer = semaphorer;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    this.semaphorer.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // receive signal then do something
            }
        }
    }

    public static void main(String[] args) {
        UseSemaphorerForSignaling use = new UseSemaphorerForSignaling();
        MN_Semaphorer semaphorer = new MN_Semaphorer(1);
        SendingThread sendingThread = use.new SendingThread(semaphorer);
        ReceivingThread receivingThread = use.new ReceivingThread(semaphorer);

        sendingThread.start();
        receivingThread.start();
    }
}
