package com.cm4j.email.pojo;

import java.util.Date;

/**
 * 发件箱
 * 
 * @author yanghao
 * 
 */
public class EmailSendRecord {

	/**
	 * 发送中 - 未验证
	 */
	public static final String STATE_SENDING = "0";
	/**
	 * 发送成功 - 已验证
	 */
	public static final String STATE_SEND_OK = "1";
	/**
	 * 发送失败 - 有退信
	 */
	public static final String STATE_SEND_FEEDBACK = "2";

	private long id;

	private long outboxId;

	private long inboxId;

	private String sendState;

	private String feedbackCode;

	private String feedbackInfo;

	private Date updateDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOutboxId() {
		return outboxId;
	}

	public void setOutboxId(long outboxId) {
		this.outboxId = outboxId;
	}

	public long getInboxId() {
		return inboxId;
	}

	public void setInboxId(long inboxId) {
		this.inboxId = inboxId;
	}

	public String getSendState() {
		return sendState;
	}

	public void setSendState(String sendState) {
		this.sendState = sendState;
	}

	public String getFeedbackCode() {
		return feedbackCode;
	}

	public void setFeedbackCode(String feedbackCode) {
		this.feedbackCode = feedbackCode;
	}

	public String getFeedbackInfo() {
		return feedbackInfo;
	}

	public void setFeedbackInfo(String feedbackInfo) {
		this.feedbackInfo = feedbackInfo;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
