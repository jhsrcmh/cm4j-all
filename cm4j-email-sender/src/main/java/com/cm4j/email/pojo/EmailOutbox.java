package com.cm4j.email.pojo;

import java.util.Date;

/**
 * 发件箱
 * 
 * @author yanghao
 * 
 */
public class EmailOutbox {
	
	/**
	 * 状态 - 禁用
	 */
	public static final String STATE_INVALID = "0";
	/**
	 * 状态 - 可用
	 */
	public static final String STATE_VALID = "1";
	/**
	 * 状态 - 待验证可用性
	 */
	public static final String STATE_TO_VERIFY = "2";
	/**
	 * 状态 - 用户名或密码错
	 */
	public static final String STATE_IDENTITY_INVALID = "3";
	/**
	 * 状态 - 无法连接到对应端口
	 */
	public static final String STATE_PORT_CONN_FAIL = "4";
	
    private long id;

    private String email;

    private String userName;

    private String password;

    private String hostname;

    private int port;

    private String state;
    
    private Date createDate;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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
}
