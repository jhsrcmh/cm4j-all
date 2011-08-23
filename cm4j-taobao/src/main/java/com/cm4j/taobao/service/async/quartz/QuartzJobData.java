package com.cm4j.taobao.service.async.quartz;

import java.util.Date;

import org.quartz.Job;

public class QuartzJobData {

	/**
	 * 处理类
	 */
	private Class<? extends Job> handlerClazz;
	/**
	 * 淘宝ID
	 */
	private Long userId;
	/**
	 * 淘宝用户sessionKey
	 */
	private String sessionKey;
	/**
	 * 定时任务执行cron
	 */
	private String cron;
	/**
	 * cron task ID
	 */
	private Long taskId;
	/**
	 * json数据
	 */
	private String jsonData;

	/**
	 * 开始结束时间
	 */
	private Date startDate, endDate;

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

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Class<? extends Job> getHandlerClazz() {
		return handlerClazz;
	}

	public void setHandlerClazz(Class<? extends Job> handlerClazz) {
		this.handlerClazz = handlerClazz;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

}
