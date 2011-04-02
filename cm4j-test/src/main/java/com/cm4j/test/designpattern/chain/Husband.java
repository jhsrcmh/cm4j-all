package com.cm4j.test.designpattern.chain;

public class Husband extends Handler {

    public Husband() {
        super(Handler.TYPE_HUSBAND);
    }

    @Override
    public void response(IWoman woman) {
        System.out.println("请求是：" + woman.getRequest() + ",type:" + woman.getType());
        System.out.println("Husband答复是：同意");
    }

}
