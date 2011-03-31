package com.cm4j.test.thread.concurrent.queue.cache.perfect.sleepstrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 每次调用都会增加sleep()时间
 * 
 * @author yanghao
 * 
 */
public class LoopSleepStrategy implements SleepStrategy {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    long sleepms = 0L;

    @Override
    public void sleep(long singleSleepTime) {
        try {
            if (sleepms < 2000)
                sleepms += singleSleepTime;

            Thread.sleep(sleepms);
            logger.debug("线程[{}] 等待{}ms", Thread.currentThread().getName(), String.valueOf(sleepms));
        } catch (InterruptedException e) {
            logger.error("thread[" + Thread.currentThread().getName() + "] sleep error", e);
        }
    }

    /**
     * 重置休眠时间
     */
    public void reset() {
        sleepms = 0L;
    }
}