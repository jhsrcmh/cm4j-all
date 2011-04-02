package com.cm4j.test.thread.concurrent.queue.cache.perfect.sleepstrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleSleepStrategy implements SleepStrategy {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void sleep(long singleSleepTime) {
        try {
            Thread.sleep(singleSleepTime);
            logger.debug("Thread[{}] sleep {} ms", Thread.currentThread().getName(), String.valueOf(singleSleepTime));
        } catch (InterruptedException e) {
            logger.error("Thread[" + Thread.currentThread().getName() + "] sleep error", e);
        }
    }

}
