package com.cm4j.test.spring.extend_point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * BeanProcessor处理器，如果启用注解则起作用
 * 
 * @author yanghao
 *
 */
//@Service
public class DemoBeanProcessor implements BeanPostProcessor{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.debug("构造前：调用postProcessBeforeInitialization()...");
        logger.debug("beanName:{},bean:{}...", beanName, bean);
        if (bean instanceof HelloWorld)
            logger.debug("Hello.userName:{}",((HelloWorld)bean).getDemoProperty());
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.debug("构造后：调用postProcessAfterInitialization()...");
        logger.debug("beanName:{},bean:{}...", beanName, bean);
        return bean;
    }

}
