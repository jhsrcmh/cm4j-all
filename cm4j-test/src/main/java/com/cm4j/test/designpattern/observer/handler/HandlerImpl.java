package com.cm4j.test.designpattern.observer.handler;

public class HandlerImpl implements IHandler {

    @Override
    public void doSomeThing(String value) {
        System.out.println(value);
    }
}
