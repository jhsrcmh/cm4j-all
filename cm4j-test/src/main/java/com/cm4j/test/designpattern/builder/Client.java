package com.cm4j.test.designpattern.builder;


/**
 * builder 模式
 * 
 * @author yanghao
 * 
 */
public class Client {

    public static void main(String[] args) {
        Director director = new Director();

        director.getABenzModel().run();

        director.getBBenzModel().run();

        director.getCBMWModel().run();

        director.getDBMWModel().run();
    }
}
