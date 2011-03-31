package com.cm4j.test.thread.basic.sync;

public class Sun extends Parent {
    private Integer i = 0;
    
    public void addInt () {
        i = this.add(i);
    }
    
    public void stdout() {
        System.out.println(i);
    }
}