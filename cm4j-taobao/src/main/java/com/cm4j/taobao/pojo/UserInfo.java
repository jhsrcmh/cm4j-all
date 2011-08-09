package com.cm4j.taobao.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "user_info", schema = "")
public class UserInfo {

	/**
	 * 状态 - 冻结 - 0
	 */
	public static String STATE_FREEZED = "0";
	
	/**
	 * 状态 - 正常 - 1
	 */
	public static String STATE_NORMAL = "1";
	
	@Id
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "user_nick", length = 40)
	private String userNick;

	@Column(name = "user_role")
	private String userRole;

	@Column(name = "user_session_key", length = 60)
	private String sessionKey;

	@Column(name = "version_no")
	private int versionNo;
	
	@Column(name = "lease_id")
	private Long leaseId; 

	@Column(name = "state")
	private String state;

	@Column(name = "update_date")
	private Date updateDate;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public int getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(int versionNo) {
		this.versionNo = versionNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getLeaseId() {
		return leaseId;
	}

	public void setLeaseId(Long leaseId) {
		this.leaseId = leaseId;
	}
}
