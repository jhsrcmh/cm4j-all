package com.cm4j.test.spring.extend_point.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Animals implements ApplicationContextAware {

    private ApplicationContext ctx;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String animalName;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    /**
     * 动物说话...
     */
    public void speak() {
        this.animalName = "小猫";
        ctx.publishEvent(new AnimalSpeakEvent(this));
        logger.debug("动物讲话，哈哈哈...");
        ctx.publishEvent(new AnimalSpeakEvent(this, "内容1", "内容2"));
    }

    /**
     * getter
     * 
     * @return
     */
    public String getAnimalName() {
        return animalName;
    }

    /**
     * setter
     * 
     * @param animalName
     */
    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }
}
