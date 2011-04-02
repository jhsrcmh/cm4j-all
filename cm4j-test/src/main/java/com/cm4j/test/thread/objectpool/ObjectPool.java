package com.cm4j.test.thread.objectpool;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectPool<E> {

    private Logger logger = LoggerFactory.getLogger(ObjectPool.class);

    // 超时单位：毫秒
    private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;

    // 最小池大小 - 低于这个则自动生成对象
    private int minPoolSize;

    // 最大池大小
    private int maxPoolSize;

    // 每次生产对象大小
    private int acquireIncrement;

    // 对象最大空闲时间 - 单位：秒
    private long maxIdleTime;

    // 获取对象超时时间 - 单位：毫秒
    private long timeout;

    // 对象工厂
    private ObjectFactory<E> objectFactory;

    // 存储对象容器
    private ConcurrentLinkedQueue<Object[]> concurrentLinkedQueue = new ConcurrentLinkedQueue<Object[]>();

    // 信号量 - 控制对象个数并阻塞
    private Semaphore semaphore = null;

    // 当前池中空闲对象的数量 - 不包含在使用的对象
    private AtomicInteger curIdleObjectCounter = new AtomicInteger(0);

    public synchronized static <E> ObjectPool<E> newObjectPool(int minPoolSize, int maxPoolSize, int initPoolSize,
            int acquireIncrement, long timeout, long maxIdleTime, ObjectFactory<E> objectFactory) {
        return new ObjectPool<E>(minPoolSize, maxPoolSize, initPoolSize, acquireIncrement, timeout, maxIdleTime,
                objectFactory);
    }

    private ObjectPool(int minPoolSize, int maxPoolSize, int initPoolSize, int acquireIncrement, long timeout,
            long maxIdleTime, ObjectFactory<E> objectFactory) {
        this.minPoolSize = minPoolSize;
        this.maxPoolSize = maxPoolSize;
        this.acquireIncrement = acquireIncrement;
        this.maxIdleTime = maxIdleTime;
        this.timeout = timeout;
        this.objectFactory = objectFactory;

        if (initPoolSize > this.maxPoolSize || this.minPoolSize > this.maxPoolSize
                || this.acquireIncrement > this.maxPoolSize)
            throw new IllegalArgumentException("参数不合法");

        semaphore = new Semaphore(maxPoolSize);

        int number = initPoolSize < this.minPoolSize ? this.minPoolSize : initPoolSize;

        // 初始化对象池
        try {
            produceObjects(number, objectFactory, timeout, TIME_UNIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeOutException e) {
            e.printStackTrace();
        }

        // 心跳检测
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        // 每隔50ms检测一次
        scheduledExecutorService.scheduleWithFixedDelay(new ProduceCheck(), 50L, 50L, TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleWithFixedDelay(new DestroyCheck(), 50L, 50L, TimeUnit.MILLISECONDS);
    }

    /**
     * 生产对象
     */
    private void produceObjects(int number, ObjectFactory<E> objectFactory, long timeout, TimeUnit unit)
            throws InterruptedException, TimeOutException {

        for (int i = 0; i < number; i++) {
            boolean acquire = semaphore.tryAcquire(timeout, unit);
            if (acquire) { // 准许生产
                Object[] obj = new Object[] { objectFactory.createObject(), System.currentTimeMillis() };
                if (concurrentLinkedQueue.offer(obj)) {
                    curIdleObjectCounter.incrementAndGet();
                    logger.debug("生产对象:{}", obj);
                    showStatus();
                }
            } else {
                showStatus();
                throw new TimeOutException("线程池已满，无法生产对象");
            }
        }
    }

    /**
     * 取出使用
     * 
     * @throws PoolIsEmptyException
     */
    public E borrowObject() throws PoolIsEmptyException {
        if (curIdleObjectCounter.get() > 0) {
            curIdleObjectCounter.decrementAndGet();
            Object[] result = concurrentLinkedQueue.poll();
            return (E) result[0];
        } else {
            showStatus();
            throw new PoolIsEmptyException("对象池为空，无法获取对象，检查调用者是否未归还对象");
        }
    }

    /**
     * 返回对象池中
     * 
     * @param e
     */
    public void returnObject(E e) {
        if (e != null)
            if (concurrentLinkedQueue.offer(new Object[] { e, System.currentTimeMillis() }))
                curIdleObjectCounter.incrementAndGet();
    }

    /**
     * 显示状态
     */
    private void showStatus() {
        logger.info("总大小/可用池空间/可用对象数:{}/{}/{}", new Object[] { maxPoolSize, semaphore.availablePermits(),
                curIdleObjectCounter.get() });
    }

    /**
     * 生产对象
     * 
     * @author yanghao
     * 
     */
    private class ProduceCheck implements Runnable {
        @Override
        public void run() {
            // 当前池可用量 小于 minPoolSize，则生产acquireIncrement个对象
            try {
                if (curIdleObjectCounter.get() < minPoolSize)
                    produceObjects(acquireIncrement, objectFactory, timeout, TIME_UNIT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeOutException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 销毁对象
     * 
     * @author yanghao
     * 
     */
    private class DestroyCheck implements Runnable {
        @Override
        public void run() {
            /*
             * 对象销毁条件： 1.已过对象存活最大时间 2.对象不在使用，即对象在池中
             */
            for (;;) {
                Object[] result = concurrentLinkedQueue.peek();
                if (result != null) {
                    E e = (E) result[0];
                    long time = (Long) result[1];
                    if (!objectFactory.isAvaliable(e) || System.currentTimeMillis() - time > maxIdleTime * 1000) {
                        objectFactory.destroy(e);
                        concurrentLinkedQueue.remove(result);
                        curIdleObjectCounter.decrementAndGet();
                        semaphore.release();
                        logger.debug("对象超时销毁：{}", result);
                        showStatus();
                        continue;
                    }
                }
                break;
            }
        }
    }

}
