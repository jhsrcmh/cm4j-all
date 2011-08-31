package com.cm4j.test.thread.concurrent.coordination.semaphore.simple_semaphore;

/**
 * 单一信号量，仅有是、否2个状态 不像Semaphore在获取多个数量后可阻塞
 * 
 * @author yanghao
 * 
 */
public class MN_Semaphorer {

    private int signal = 0;
    
    private int total;
    
    public MN_Semaphorer(int total) {
        super();
        this.total = total;
    }

    /**
     * The take() method sends a signal which is stored internally in the
     * Semaphorer
     * @throws InterruptedException 
     * 
     */
    public synchronized void acquire() throws InterruptedException {
        while (total == this.signal)
            wait();
        
        this.signal ++;
        notify();
        
        System.out.println("acquire()");
    }

    /**
     * The release() method waits for a signal. When received the signal flag is
     * cleared again, and the release() method exited.
     * 
     * @throws InterruptedException
     */
    public synchronized void release() throws InterruptedException {
        while (this.signal ==0)
            wait();

        this.signal --;
        notify();
        
        System.out.println("release()");
    }

}
