package com.cm4j.taobao.api.common;

import org.apache.commons.lang.math.NumberUtils;

import com.cm4j.core.utils.ConfigurableConstants;

public class APIConstants extends ConfigurableConstants {

	static {
		init("taobao-api.properties");
	}

	/**
	 * 时间戳，插件需要对该时间戳进行验证<br />
	 * 官方建议误差在5分钟以内，最长不超过30分钟
	 */
	public static final String TIME_STAMP = "ts";

	/**
	 * 用户session
	 */
	public static final String TOP_SESSION = "top_session";
	/**
	 * 当前用户ID - 即uid，用户不登录则不传
	 */
	public static final String VISITOR_ID = "visitor_id";
	/**
	 * 当前用户昵称 - 用户不登录则不传
	 */
	public static final String VISITOR_NICK = "visitor_nick";
	/**
	 * 当前用户角色 - 5-未登录用户
	 */
	public static final String VISITOR_ROLE = "visitor_role";
	/**
	 * 当前用户权限
	 */
	public static final String VISITOR_PRIVILEGE = "visitor_privilege";

	/**
	 * 淘宝容器地址 -> 自动登陆使用
	 * 
	 * @return
	 */
	public static String getTaobaoContainerUrl() {
		return getValue("taobao.api.container.url");
	}

	/*
	 * ====================Properties获取============================
	 */
	/**
	 * 调用淘宝API超时时间
	 * 
	 * @return
	 */
	public static int getApplicationTimeout() {
		return NumberUtils.toInt(getValue("taobao.api.timeout"));
	}

	/**
	 * 淘宝应用key
	 * 
	 * @return
	 */
	public static String getAppKey() {
		return getValue("taobao.api.app_key");
	}

	/**
	 * 淘宝app密钥
	 * 
	 * @return
	 */
	public static String getAppSecret() {
		return getValue("taobao.api.app_secret");
	}

	/**
	 * 淘宝应用地址 -> 调用api使用
	 * 
	 * @return
	 */
	public static String getTaobaoServiceUrl() {
		return getValue("taobao.api.service.url");
	}
	
	/**
	 * 查询H2信息
	 * @param key
	 * @return
	 */
	public static String getH2Info(String key){
		return getValue("h2." + key);
	}
}
