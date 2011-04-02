package com.cm4j.test.designpattern.observer.handler;

/**
 * 观察者模式：由上层的观察端调用被观察端 notify(Object... obj) <br/>
 * 本类handler：由被调用者主动调用监听器的map方法，基本模式一致
 * 
 * @author yanghao
 */
public class Client {

    public static void main(String[] args) {
        Listener listener = new Listener();
        listener.registerHandler(new HandlerImpl());
        listener.exec("yh");
    }
}
