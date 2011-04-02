package com.cm4j.test.thread.concurrent.queue.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.cm4j.test.spring.dal.batch.BatchInsertUsage;

/**
 * 缓冲队列 比如可用于批量插入数据
 * 
 * @author yanghao
 * 
 */
@Service
public class QueuePoolAndCache {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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

    private AtomicInteger waitExecCounter = new AtomicInteger(0);

    // 注册个数
    private static int execNumber = 20000;

    @Autowired
    private BatchInsertUsage batchInsertUsage;

    public QueuePoolAndCache() {

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
                    boolean flag = blockingQueue.offer(new Object[] { "TTTT_XA0211_G_" + counter });
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

    private void exec() {
        int number = 0;
        long start = System.nanoTime();
        while (true) {
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
            int currentWaitExecTime = waitExecCounter.incrementAndGet();
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
            waitExecCounter.set(0);

            List<Object[]> list = new ArrayList<Object[]>(batchExecSize);
            blockingQueue.drainTo(list, batchExecSize);

            // 取到list之后执行批处理
            logger.debug("批处理执行，大小：{}", list.size());
            batchInsertUsage.insertPassport(BatchInsertUsage.INSERT_PASSPORT, list);
            number += list.size();
            if (number >= execNumber) {
                logger.debug("执行完成");
                break;
            }
        }

        long end = System.nanoTime();
        float sec = (float) (end - start) / 1000 / 1000 / 1000;
        logger.debug("执行时间:{}", sec);
        logger.debug("每秒执行个数:{}", execNumber / sec);
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        QueuePoolAndCache pool = ctx.getBean(QueuePoolAndCache.class);
        pool.exec();
    }
}
