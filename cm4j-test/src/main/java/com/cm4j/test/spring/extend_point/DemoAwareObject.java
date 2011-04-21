package com.cm4j.test.spring.extend_point;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class DemoAwareObject implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void setBeanName(String name) {
		System.out.println("====================================");
		logger.debug("调用setBeanName(),name:{}", name);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		logger.debug("调用setBeanFactory(),beanFactory:{}", beanFactory);
	}

	@PostConstruct
	public void customInit() {
		logger.debug("调用customInit()...");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.debug("调用afterPropertiesSet()...");
	}

	public void exec() {
		logger.debug("执行业务逻辑");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		logger.debug("调用applicationContext(),context:{}", applicationContext);
	}
}
