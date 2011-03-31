package com.cm4j.test.thread.concurrent.queue.cache.perfect.sleepstrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于循环中-每次调用都会增加sleep()时间，默认上限sleep时间为2000ms
 * 
 * @author yanghao
 * 
 */
public class IncrementSleepStrategy implements SleepStrategy {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    long sleepms = 0L;

    private long maxSleepTime = 2000L;

    @Override
    public void sleep(long singleSleepTime) {
        if (sleepms + singleSleepTime <= maxSleepTime) {
            sleepms += singleSleepTime;
        }
        try {
            Thread.sleep(sleepms);
            logger.debug("Thread[{}] sleep {} ms", Thread.currentThread().getName(), String.valueOf(sleepms));
        } catch (InterruptedException e) {
            logger.error("Thread[" + Thread.currentThread().getName() + "] sleep error", e);
        }
    }

    /**
     * 重置休眠时间
     */
    public void reset() {
        sleepms = 0L;
    }

    public void setMaxSleepTime(long maxSleepTime) {
        this.maxSleepTime = maxSleepTime;
    }
}