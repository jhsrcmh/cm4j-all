package com.cm4j.taobao.service.async.quartz;

public class QuartzJobData {

	/**
	 * 淘宝ID
	 */
	private Long userId;
	/**
	 * 淘宝用户sessionKey
	 */
	private String sessionKey;
	/**
	 * json数据
	 */
	private String jsonData;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

}
