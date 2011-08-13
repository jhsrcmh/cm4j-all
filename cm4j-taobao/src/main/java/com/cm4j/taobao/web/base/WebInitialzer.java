package com.cm4j.taobao.web.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

import com.cm4j.taobao.service.async.quartz.QuartzService;

/**
 * 上下文初始化用于初始化
 * 
 * @author yang.hao
 * @since 2011-8-10 下午04:27:43
 * 
 */
//@Service
public class WebInitialzer implements BeanFactoryAware {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		// todo 多次调用?
		logger.debug("context aware,quartz init...");
		QuartzService quartzService = beanFactory.getBean(QuartzService.class);
		quartzService.startJobs();
	}
}
