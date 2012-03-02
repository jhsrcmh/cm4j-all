package com.cm4j.web.utils;

import com.cm4j.core.utils.ConfigurableConstants;

public class WebConstants extends ConfigurableConstants {

	static {
		init("web-api.properties");
	}
	
	/**
	 * 查询H2信息
	 * 
	 * @param key
	 * @return
	 */
	public static String getH2Info(String key) {
		return getValue("h2." + key);
	}
	
	public static String getLoginUrl (){
		return getValue("web.login.url");
	}
	
}
