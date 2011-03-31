package com.cm4j.test.thread.concurrent.copyonwritearraylist;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * {@link CopyOnWriteArrayList} 使用场景：
 * 
 * 集合从内部将它的内容复制到一个没有修改的新数组，这样读者访问数组内容时就不会产生同步成本
 * （因为他们从来不是在易变数据上操作）。本质上讲，CopyOnWriteArrayList 很适合处理 ArrayList
 * 经常让我们失败的这种场景：读取频繁，但很少有写操作的集合，例如 监听者模式
 * 
 * @author yanghao
 * 
 */
public class CopyOnWriteArrayListUsage {
    private class Listener {
        public void handle() {
            System.out.println("logical business");
        }
    }

    private List<Listener> listeners = new CopyOnWriteArrayList<Listener>();

    public boolean addListener(Listener listener) {
        return listeners.add(listener);
    }

    public void doXXX() {
        for (Listener listener : listeners) {
            listener.handle();
        }
    }
}
