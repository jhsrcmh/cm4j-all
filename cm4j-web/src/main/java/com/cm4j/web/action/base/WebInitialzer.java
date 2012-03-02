package com.cm4j.web.action.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 上下文初始化用于初始化
 * 
 * @author yang.hao
 * @since 2011-8-10 下午04:27:43
 * 
 */
public class WebInitialzer {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public void init() throws Exception {
		logger.debug("WebInitialzer init...");
		// web 初始化操作
	}

}
