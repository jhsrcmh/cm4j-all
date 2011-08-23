package com.cm4j.taobao.web.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm4j.taobao.service.async.quartz.QuartzService;

/**
 * 上下文初始化用于初始化
 * 
 * @author yang.hao
 * @since 2011-8-10 下午04:27:43
 * 
 */
@Service
public class WebInitialzer implements InitializingBean {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private QuartzService quartzService;

	@Override
	public void afterPropertiesSet() throws Exception {
		// todo 多次调用?
		logger.debug("context aware,quartz init...");
		quartzService.startJobs();
	}

}
