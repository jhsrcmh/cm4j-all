package com.cm4j.test.designpattern.chain;

import java.util.ArrayList;

import org.junit.Test;

public class Client {

    @Test
    public void usage() {
        ArrayList<IWoman> arrayList = new ArrayList<IWoman>();
        for (int i = 0; i < 4; i++)
            arrayList.add(new Woman(i, "我要去逛啊"));

        Handler father = new Father();
        Handler husband = new Husband();
        Handler son = new Son();

        father.setNext(husband);
        husband.setNext(son);

        for (IWoman iWoman : arrayList)
            father.handleMessage(iWoman);
    }
}
