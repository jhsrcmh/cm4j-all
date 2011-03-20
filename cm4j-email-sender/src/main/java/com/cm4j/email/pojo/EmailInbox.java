package com.cm4j.email.pojo;

import java.util.Date;

/**
 * 收件箱 - 接受者邮件
 * 
 * @author yanghao
 * 
 */
public class EmailInbox {

	/**
	 * 状态 - 禁用
	 */
	public static final String STATE_INVALID = "0";
	/**
	 * 状态 - 发送成功
	 */
	public static final String STATE_VALID = "1";
	/**
	 * 状态 - 待发送
	 */
	public static final String STATE_TO_SEND = "2";
	/**
	 * 状态 - 已发送，待验证
	 */
	public static final String STATE_TO_VERIFY = "3";
	/**
	 * 状态 - 发送失败
	 */
	public static final String STATE_IDENTITY_INVALID = "4";

	private long id;
	private String email;
	private String state;
	private Date createDate;
	private Date updateDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
