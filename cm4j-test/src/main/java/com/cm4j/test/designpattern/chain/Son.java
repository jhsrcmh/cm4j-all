package com.cm4j.test.designpattern.chain;

public class Son extends Handler {

    public Son() {
        super(Handler.TYPE_SON);
    }

    @Override
    public void response(IWoman woman) {
        System.out.println("请求是：" + woman.getRequest() + ",type:" + woman.getType());
        System.out.println("Son答复是：同意");
    }

}
