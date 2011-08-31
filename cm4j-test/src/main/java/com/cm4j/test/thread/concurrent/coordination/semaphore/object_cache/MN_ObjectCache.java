package com.cm4j.test.thread.concurrent.coordination.semaphore.object_cache;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Semaphore是一个计数器，在计数器不为0的时候对线程就放行，一旦达到0，那么所有请求资源的新线程都会被阻塞，
 * 包括增加请求到许可的线程，也就是说Semaphore不是可重入的。
 * 每一次请求一个许可都会导致计数器减少1，同样每次释放一个许可都会导致计数器增加1，一旦达到了0，
 * 新的许可请求线程将被挂起。缓存池就是使用此思想来实现的，比如链接池、对象池等。
 * 
 * @author yanghao
 * 
 * @param <T>
 */
public class MN_ObjectCache<T> {

    public interface ObjectFactory<T> {
        T makeObject();
    }

    class Node {
        T obj;

        Node next;
    }

    final int capacity;

    final ObjectFactory<T> factory;

    final Lock lock = new ReentrantLock();

    final Semaphore semaphore;

    private Node head;

    private Node tail;

    public MN_ObjectCache(int capacity, ObjectFactory<T> factory) {
        this.capacity = capacity;
        this.factory = factory;
        this.semaphore = new Semaphore(this.capacity);
        this.head = null;
        this.tail = null;
    }

    public T getObject() throws InterruptedException {
        semaphore.acquire();
        return getNextObject();
    }

    private T getNextObject() {
        lock.lock();
        try {
            if (head == null) {
                return factory.makeObject();
            } else {
                Node ret = head;
                head = head.next;
                if (head == null)
                    tail = null;
                ret.next = null;// help GC
                return ret.obj;
            }
        } finally {
            lock.unlock();
        }
    }

    public void returnObject(T t) {
        returnObjectToPool(t);
        semaphore.release();
    }
    
    private void returnObjectToPool(T t) {
        lock.lock();
        try {
            Node node = new Node();
            node.obj = t;
            if (tail == null) {
                head = tail = node;
            } else {
                tail.next = node;
                tail = node;
            }

        } finally {
            lock.unlock();
        }
    }

}