package com.cm4j.test.thread.concurrent.queue.cache.perfect;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cm4j.test.spring.dal.batch.BatchInsertUsage;
import com.cm4j.test.thread.concurrent.queue.cache.perfect.BufferHandler;
import com.cm4j.test.thread.concurrent.queue.cache.perfect.BufferPool;
import com.cm4j.test.thread.concurrent.queue.cache.perfect.BufferPoolConfiguration;

public class BlockingQueuePoolTest {

    private static final Logger logger = LoggerFactory.getLogger(BlockingQueuePoolTest.class);

    /**
     * 不能用junit去做测试，它会结束主线程...
     * 
     * @param args
     */
    public static void main(String[] args) {
        BufferPoolConfiguration configuration = new BufferPoolConfiguration();
        configuration.setConsumerThreadNum(2);

        final ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        BufferPool<Object[]> pool = new BufferPool<Object[]>(configuration, new BufferHandler<Object[]>() {

            @Override
            public void onElementsReceived(List<Object[]> e) {
                BatchInsertUsage batch = ctx.getBean(BatchInsertUsage.class);
                batch.insertPassport(BatchInsertUsage.INSERT_PASSPORT, e);
            }

            @Override
            public void unexceptedException(Exception exception) {
                // TODO Auto-generated method stub

            }

        });

        for (int i = 0; i < 10; i++) {
            String name = "TTTT_XA0210_B_" + i;
            boolean flag = pool.offer(new Object[] { name }, 3, TimeUnit.SECONDS);
            if (!flag) {
                logger.error("不能放入队列中：{}", name);
            }
        }

        System.exit(0);
    }
}
