package com.cm4j.test.designpattern.chain;

public interface IWoman {
    public Class<? extends Handler> getHandler();
    
    public String getRequest();
}
