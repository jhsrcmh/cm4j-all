package com.cm4j.test.designpattern.chain;

public class Father extends Handler {

    @Override
    public void handle(IWoman woman) {
        System.out.println("请求是：" + woman.getRequest() + ",type:" + woman.getHandler());
        System.out.println("Father答复是：同意");
    }

}
