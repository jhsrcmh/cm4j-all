package com.cm4j.test.thread.concurrent.queue.cache.perfect;

import java.util.List;

public interface BufferHandler<E> {

    /**
     * 在收到对象时执行批处理操作
     * 
     * @param e
     */
    public void onElementsReceived(List<E> e);

    /**
     * 非预期异常处理 - 处理onElementsReceived()未捕获的异常<br />
     * 批处理异常不在此列，需在onElementsReceived内部处理捕获
     * 
     * @param throwable
     */
    public void unexceptedException(Exception exception);

}
