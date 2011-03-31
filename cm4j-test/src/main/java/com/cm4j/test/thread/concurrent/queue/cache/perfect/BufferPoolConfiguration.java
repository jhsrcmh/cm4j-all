package com.cm4j.test.thread.concurrent.queue.cache.perfect;

/**
 * 缓冲池配置
 * 
 * @author yanghao
 * 
 */
public class BufferPoolConfiguration {

    /**
     * 缓冲池名称
     */
    private String poolName = "bufferPool";

    /**
     * 队列总大小
     */
    private int queueSize = 500;

    /**
     * 最大批处理大小
     */
    private int maxBatchExecSize = 300;

    /**
     * 最小批处理个数 - 队列中达不到最小量，则等待 0.25秒 * maxWaitExecTime
     */
    private int minBatchExecSize = 20;

    /**
     * 有数据但未达到最小批处理时的最大等待次数
     */
    private int maxWaitExecTime = 2;

    /**
     * 消费线程数量
     */
    private int consumerThreadNum = 1;

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public int getMinBatchExecSize() {
        return minBatchExecSize;
    }

    public void setMinBatchExecSize(int minBatchExecSize) {
        this.minBatchExecSize = minBatchExecSize;
    }

    public int getMaxWaitExecTime() {
        return maxWaitExecTime;
    }

    public void setMaxWaitExecTime(int maxWaitExecTime) {
        this.maxWaitExecTime = maxWaitExecTime;
    }

    public int getMaxBatchExecSize() {
        return maxBatchExecSize;
    }

    public void setMaxBatchExecSize(int maxBatchExecSize) {
        this.maxBatchExecSize = maxBatchExecSize;
    }

    public int getConsumerThreadNum() {
        return consumerThreadNum;
    }

    public void setConsumerThreadNum(int consumerThreadNum) {
        this.consumerThreadNum = consumerThreadNum;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }
}
