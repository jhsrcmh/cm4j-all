package com.cm4j.test.designpattern.observer.multieventListener;

import java.util.EventListener;
import java.util.EventObject;

/**
 * 事件监听接口
 * 
 * @author yanghao
 * 
 */
public interface DemoListener extends EventListener {

    public void onEvent(EventObject dm);
}
