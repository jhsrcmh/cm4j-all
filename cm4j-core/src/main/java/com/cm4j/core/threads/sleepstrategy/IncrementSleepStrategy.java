package com.cm4j.core.threads.sleepstrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IncrementSleepStrategy implements SleepStrategy {

	private long sleepms = 0L;
	private long maxSleepms = 0L;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public void sleep(long ms) {
		sleepms += ms;
		if (sleepms <= maxSleepms)
			try {
				Thread.sleep(sleepms);
				logger.debug("thread [{}] sleep {} ms", Thread.currentThread().getName(), sleepms);
			} catch (InterruptedException e) {
				logger.error("thread [" + Thread.currentThread().getName() + "] sleep error", e);
			}

	}

	public void reset() {
		this.sleepms = 0L;
	}

	public void setMaxSleepms(long maxSleepms) {
		this.maxSleepms = maxSleepms;
	}
}
