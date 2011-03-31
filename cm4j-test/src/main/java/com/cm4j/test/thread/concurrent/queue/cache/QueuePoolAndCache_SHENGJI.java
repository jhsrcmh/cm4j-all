package com.cm4j.test.thread.concurrent.queue.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.cm4j.test.spring.dal.batch.BatchInsertUsage;

/**
 * 缓冲队列 比如可用于批量插入数据 - 增加多线程性能测试
 * 
 * @author yanghao
 * 
 */
@Service
public class QueuePoolAndCache_SHENGJI {

    private final static Logger logger = LoggerFactory.getLogger(QueuePoolAndCache_SHENGJI.class);

    private BlockingQueue<Object[]> blockingQueue;

    /**
     * 队列总大小
     */
    private int queueSize = 500;

    /**
     * 批处理大小
     */
    private int batchExecSize = 300;

    /**
     * 最大等待执行次数
     */
    private int maxWaitExecTime = 3;

    /**
     * 有缓冲时最小批处理个数
     */
    private int minBatchExecSize = 20;

    // 注册个数
    private static int execNumber = 20000;

    @Autowired
    private BatchInsertUsage batchInsertUsage;
    
    private AtomicInteger execCounter = new AtomicInteger(0);

    public QueuePoolAndCache_SHENGJI() {

        blockingQueue = new ArrayBlockingQueue<Object[]>(queueSize);

        // 关闭应用时执行的代码
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                logger.debug("应用关闭，执行ShutdownHook");
            }
        });

        // 向队列中放入对象
        new Thread() {
            public void run() {
                int counter = 0;
                while (counter < execNumber) {
                    boolean flag = blockingQueue.offer(new Object[] { "TTTT_XA0211_O_" + counter });
                    if (!flag) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        counter++;
                    }
                }
                logger.debug("放入队列个数：{}", counter);
            };
        }.start();
    }

    public class ConsumerThread implements Runnable {
        // 等待执行次数
        private int waitExecCounter;
        
        private CyclicBarrier barrier;
        public ConsumerThread(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            List<Object[]> list = new ArrayList<Object[]>(batchExecSize);
            try {
                barrier.await();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            
            while (true) {
                // 如果执行数量达到，则退出循环(测试判断语句)
                if (execCounter.get() >= execNumber) {
                    break;
                }
                
                // 当前队列大小
                int queueCurrentSize = blockingQueue.size();
                if (queueCurrentSize == 0) {
                    logger.debug("当前队列为0，等待0.5秒继续");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                } else {
                    logger.debug("当前队列大小：{}/{}", queueCurrentSize, queueSize);
                }

                // 等待执行次数+1
                int currentWaitExecTime = ++waitExecCounter;
                // 队列大小小于批处理大小且当前等待次数不大于最大等待次数 ===> 等待
                if (queueCurrentSize < minBatchExecSize && currentWaitExecTime <= maxWaitExecTime) {
                    logger.debug(
                            "不满足条件，等待0.25秒：queueCurrentSize:{},minBatchExecSize:{},currentWaitExecTime:{},maxWaitExecTime:{}",
                            new Object[] { queueCurrentSize, minBatchExecSize, currentWaitExecTime, maxWaitExecTime });
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                // 本次可以执行，故等待次数归0
                waitExecCounter = 0;

                list.clear();
                blockingQueue.drainTo(list, batchExecSize);

                // 取到list之后执行批处理
                logger.debug("批处理执行，大小：{}", list.size());
                batchInsertUsage.insertPassport(BatchInsertUsage.INSERT_PASSPORT, list);

                int cur = execCounter.addAndGet(list.size());
                logger.debug("当前已执行数量：{}",cur);
            }
            
            try {
                barrier.await();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        QueuePoolAndCache_SHENGJI pool = ctx.getBean(QueuePoolAndCache_SHENGJI.class);
       
        CyclicBarrier barrier = new CyclicBarrier(3);
        new Thread(pool.new ConsumerThread(barrier),"thread-001").start();
        new Thread(pool.new ConsumerThread(barrier),"thread-002").start();
        
        barrier.await();
        long start = System.nanoTime();
        barrier.await();
        long end = System.nanoTime();
        float sec = (float) (end - start) / 1000 / 1000 / 1000;
        logger.debug("执行时间:{}", sec);
        logger.debug("每秒执行个数:{}", execNumber / sec);
    }
}
