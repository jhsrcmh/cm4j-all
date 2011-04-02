package com.cm4j.test.thread.concurrent.queue.cache.perfect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cm4j.test.thread.concurrent.queue.cache.perfect.sleepstrategy.IncrementSleepStrategy;
import com.cm4j.test.thread.concurrent.queue.cache.perfect.sleepstrategy.SingleSleepStrategy;

public class BufferPool<E> {

    private final static Logger logger = LoggerFactory.getLogger(BufferPool.class);

    private BufferPoolConfiguration configuration;

    private BufferHandler<E> handler;

    private BlockingQueue<E> blockingQueue;

    // 计数器，批处理执行个数
    private AtomicLong batchExecCounter = new AtomicLong(0);

    // 是否可以offer
    private boolean shouldOffer = true;

    // 用于存放启动时添加的consumer线程，在ShutdownHook中关闭
    private Set<ConsumerThread> listenedThreads = new LinkedHashSet<ConsumerThread>();

    public BufferPool(BufferPoolConfiguration configuration, BufferHandler<E> handler) {
        this.configuration = configuration;
        this.handler = handler;
        this.blockingQueue = new ArrayBlockingQueue<E>(configuration.getQueueSize());

        // 系统停止时业务处理
        Runtime.getRuntime().addShutdownHook(new Thread("shutdownThread") {
            @Override
            public void run() {
                logger.info("应用接受到关闭指令，执行ShutdownHook");
                // 禁止向队列里放数据
                shouldOffer = false;

                IncrementSleepStrategy incrementSleepStrategy = new IncrementSleepStrategy();
                int size = 0;
                while (true) {
                    size = blockingQueue.size();

                    // 当前队列中存在数据，线程等待
                    if (size == 0) {
                        // 不存在，等待3秒，让其他线程处理剩余数据
                        new SingleSleepStrategy().sleep(3000);

                        // 退出所有线程
                        logger.debug("队列中无数据，退出所有线程");
                        for (Iterator<ConsumerThread> itor = listenedThreads.iterator(); itor.hasNext();) {
                            ConsumerThread t = itor.next();
                            t.setExit(true);
                            logger.info("线程{}退出....", t.getName());
                        }
                        // 退出循环
                        break;
                    }

                    // 当前队列中存在数据，线程等待
                    logger.debug("队列中有数据，等待consumer执行完queue");

                    // 线程等待
                    incrementSleepStrategy.sleep(250L);
                }

            }
        });

        // 启动消费监听线程
        for (int i = 0; i < configuration.getConsumerThreadNum(); i++) {
            ConsumerThread t = new ConsumerThread();
            t.setName("BufferPool<Consumer-" + i + ">");
            listenedThreads.add(t);
            t.start();
        }
    }

    /**
     * <pre>
     * 此方法会导致调用方阻塞
     * 
     * 向队列中放入值，可能有3种情况放不进
     * 1.队列异常
     * 2.队列满==>此时调用者阻塞
     * 2.程序关闭时调用ShutdownHook，此时不能向队列中放入数据
     * </pre>
     * 
     * @param e
     * @return
     */
    public void put(E e) {
        if (!shouldOffer) {
            logger.warn("程序关闭，shouldOffer = false，不能向queue中放入数据");
            return;
        }
        try {
            blockingQueue.put(e);
        } catch (Exception e1) {
            logger.error("offer to queue error", e1);
        }
    }

    /**
     * <pre>
     * 向队列中放入值，可能有2种情况放不进
     * 1.队列异常
     * 2.程序关闭时调用ShutdownHook，此时不能向队列中放入数据
     * </pre>
     * 
     * @param e
     * @return
     */
    public boolean offer(E e, long timeout, TimeUnit timeUnit) {
        boolean flag = false;
        if (!shouldOffer) {
            logger.warn("程序关闭，shouldOffer = false，不能向queue中放入数据");
            return flag;
        }
        try {
            flag = blockingQueue.offer(e, timeout, timeUnit);
        } catch (Exception e1) {
            logger.error("offer to queue error", e1);
        }
        return flag;
    }

    /**
     * 消费进程
     * 
     * @author yanghao
     * 
     */
    private class ConsumerThread extends Thread {
        // 等待执行次数
        private int waitExecCounter = 0;

        /**
         * 退出标志
         */
        private boolean isExit = false;

        @Override
        public void run() {
            List<E> list = new ArrayList<E>(configuration.getMaxBatchExecSize());

            SingleSleepStrategy singleSleepStrategy = new SingleSleepStrategy();
            IncrementSleepStrategy incrementSleepStrategy = new IncrementSleepStrategy();

            // 退出标志判断
            while (!isExit) {
                // 当前队列大小
                int queueCurrentSize = blockingQueue.size();
                if (queueCurrentSize == 0) {
                    logger.debug("当前队列为0，等待有数据再继续");
                    incrementSleepStrategy.sleep(250L);
                    continue;
                } else {
                    logger.info("当前队列大小：{}/{}", queueCurrentSize, configuration.getQueueSize());
                    incrementSleepStrategy.reset();
                }

                // 等待执行次数+1
                int currentWaitExecTime = ++waitExecCounter;
                // 队列大小小于批处理大小且当前等待次数不大于最大等待次数 ===> 等待
                if (queueCurrentSize < configuration.getMinBatchExecSize()
                        && currentWaitExecTime <= configuration.getMaxWaitExecTime()) {
                    logger.debug(
                            "pool大小介于[1,MinBatchExecSize]，等待0.2秒：queueCurrentSize:{},minBatchExecSize:{},currentWaitExecTime:{},maxWaitExecTime:{}",
                            new Object[] { queueCurrentSize, configuration.getMinBatchExecSize(), currentWaitExecTime,
                                    configuration.getMaxWaitExecTime() });
                    singleSleepStrategy.sleep(200L);
                    continue;
                }

                // 本次可以执行，故等待次数归0
                waitExecCounter = 0;

                list.clear();
                blockingQueue.drainTo(list, configuration.getMaxBatchExecSize());

                // 取到list之后执行批处理
                try {
                    if (!list.isEmpty())
                        handler.onElementsReceived(list);
                } catch (Exception e) {
                    logger.error("handler发生异常，需handler自己处理", e);
                    handler.unexceptedException(e);
                }

                logger.info("批处理数量:本次循环/总数量：{}/{}", list.size(), batchExecCounter.addAndGet(list.size()));
            }
        }

        public void setExit(boolean isExit) {
            this.isExit = isExit;
        }
    }
}
