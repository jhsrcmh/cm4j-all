package com.cm4j.test.thread.concurrent.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * <pre>
 * task submitter把任务交给Executor执行，他们之间需要一种通讯手段，具体实现即Future。
 * Future通常包括get(阻塞至任务完成)，cancel，get(timeout)等
 * 
 * 有两种任务：Runnable Callable(需要返回值的任务)
 * </pre>
 * 
 * @author yanghao
 * 
 */
public class Executor {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Executors是Executor的工厂类
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Object> task = new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(3000);
                return "result object";
            }
        };

        Future<Object> future = executor.submit(task);
        try {
            System.out.println(future.get(1,TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            System.out.println("超时了");
            e.printStackTrace();
        }
        
        executor.shutdown();
    }
}
