package com.cm4j.test.designpattern.observer.eventlistener;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 抽象事件源
 * 
 * @author yanghao
 * 
 */
public abstract class AbstractEventSource {

    private final Set<DemoListener> repository = new LinkedHashSet<DemoListener>();

    public void registerListener(DemoListener dl) {
        repository.add(dl);
    }

    /**
     * 调用已注册的监听接口
     */
    public void publishEvent(DemoEvent e) {
        Iterator<DemoListener> enu = repository.iterator();
        while (enu.hasNext()) {
            DemoListener demoListener = (DemoListener) enu.next();
            demoListener.onEvent(e);
        }
    }
}
