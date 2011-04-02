package com.cm4j.test.designpattern.observer.eventlistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActualEventSource extends AbstractEventSource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String name;

    public void exec() {
        this.name = "事件源";
        super.publishEvent(new DemoEvent(this));
        logger.debug("执行具体业务逻辑");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
