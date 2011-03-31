package com.cm4j.test.designpattern.observer.eventlistener;

import java.util.EventListener;

/**
 * 事件监听接口
 * 
 * @author yanghao
 * 
 */
public interface DemoListener extends EventListener {

    public void onEvent(DemoEvent dm);
}
