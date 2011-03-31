package com.cm4j.test.designpattern.observer.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Listener {

    private List<IHandler> handlerList = new ArrayList<IHandler>();

    public void registerHandler(IHandler handler) {
        this.handlerList.add(handler);
    }

    public void exec(String value) {
        for (Iterator<IHandler> iterator = handlerList.iterator(); iterator.hasNext();) {
            // 这里可以进行循环 从队列中获取key和value，再调用handler方法，可实现事件监听
            iterator.next().doSomeThing(value);
        }
    }
}
