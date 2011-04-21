package com.cm4j.test.thread.objectpool;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectPoolUsage {

    private static final Logger logger = LoggerFactory.getLogger(ObjectPoolUsage.class);
    
    public static final int testNum = 1000000;
    
    private AtomicInteger getNum = new AtomicInteger(0);

    public class GetThread implements Runnable {
        private ObjectPool<String> objectPool;

        private CyclicBarrier barrier;

        public GetThread(ObjectPool<String> objectPool, CyclicBarrier barrier) {
            this.objectPool = objectPool;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                barrier.await();
            } catch (Exception e1) {
                e1.printStackTrace();
            } 
            while (true) {
                if (getNum.get() >= testNum)
                    break;
                
                String obj = null;
                try {
                    obj = objectPool.borrowObject();
                    int num = getNum.incrementAndGet();
//                    logger.debug("线程get对象：{}", obj);
//                    logger.debug("获取对象个数：{}",num);
                } catch (PoolIsEmptyException e) {
                    e.printStackTrace();
                } finally {
                    objectPool.returnObject(obj);
                }
            }
            try {
                barrier.await();
            } catch (Exception e1) {
                e1.printStackTrace();
            } 
        }
    }

    public static void main(String[] args) {
        int minPoolSize = 5, maxPoolSize = 10, initSize = 3, increaseSize = 2, timeout = 3, maxIdleTime = 3;
        
        ObjectFactory<String> factory = new ObjectFactory<String>() {
            @Override
            public String createObject() {
                return "Obj_" + new Random().nextInt(Integer.MAX_VALUE);
            }

            @Override
            public void destroy(String e) {
                // 销毁对象操作
                return;
            }
            
            @Override
            public boolean isAvaliable(String e) {
                return true;
            }
        };
        ObjectPool<String> pool = ObjectPool.newObjectPool(minPoolSize, maxPoolSize, initSize, increaseSize, timeout,
                maxIdleTime, factory);

        ObjectPoolUsage usage = new ObjectPoolUsage();
        int threadNum = 3;
        CyclicBarrier barrier = new CyclicBarrier(threadNum + 1);
        GetThread thread = usage.new GetThread(pool, barrier);
        for (int i = 0; i < threadNum; i++) {
            new Thread(thread, "GetThread-" + i).start();
        }
        
        try {
            barrier.await();
        } catch (Exception e1) {
            e1.printStackTrace();
        } 
        long start = System.nanoTime();
        try {
            barrier.await();
        } catch (Exception e1) {
            e1.printStackTrace();
        } 
        long end = System.nanoTime();
        float time = (float)(end - start)/1000/1000/1000;
        logger.debug("time/s:{}",time);
        logger.debug("tps:{}",testNum/time);
        
    }
}
