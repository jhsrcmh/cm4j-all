package com.cm4j.test.thread.basic.sync;

public class Parent {

    public synchronized int add(int i) {
        return ++ i;
    }
}
