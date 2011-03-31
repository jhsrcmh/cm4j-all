package com.cm4j.test.thread.objectpool;

public interface ObjectFactory<E> {

    /**
     * 对象创建操作
     * 
     * @return
     */
    public E createObject();
    
    /**
     * 对象销毁操作
     * 
     * @param e
     */
    public void destroy(E e);
    
    /**
     * 对象是否可用，不可用则从对象池中销毁
     * 
     * @param e
     * @return
     */
    public boolean isAvaliable (E e);
    
}
