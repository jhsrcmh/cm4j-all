package com.cm4j.test.thread.basic;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异步线程池,通过spring注入初始化参数
 * 
 * @author Yang Hao
 * 
 */
public class ThreadPool {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private ThreadPoolExecutor threadPoolExecutor;

	private int corePoolSize = 10, maximumPoolSize = 10;
	private long keepAliveTime = 5;
	private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
	private int blockingQueueNum;

	/**
	 * 获取threadPoolExecutor
	 */
	@PostConstruct
	public void init() {
		this.threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit,
				new ArrayBlockingQueue<Runnable>(blockingQueueNum), new ThreadPoolExecutor.CallerRunsPolicy());
	}

	/**
	 * 添加并执行任务
	 * 
	 * @param task
	 */
	public void executeTask(Runnable task) {
		try {
			if (this.threadPoolExecutor == null)
				this.init();
			
			this.threadPoolExecutor.execute(task);
		} catch (Exception e) {
			logger.error("threadErr", e);
		}
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	public void setBlockingQueueNum(int blockingQueueNum) {
		this.blockingQueueNum = blockingQueueNum;
	}

}
