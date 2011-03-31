package com.cm4j.test.designpattern.chain;

public class Father extends Handler {

    public Father() {
        super(Handler.TYPE_FATHER);
    }
    
    @Override
    public void response(IWoman woman) {
        System.out.println("请求是：" + woman.getRequest() + ",type:" + woman.getType());
        System.out.println("Father答复是：同意");
    }

}
