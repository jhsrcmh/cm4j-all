package com.cm4j.email.sender;

import java.util.Properties;

/**
 * 发件箱信息
 * 
 * @author Administrator
 * 
 */
public class SendBoxInfo {
	private String defaultEncoding = "UTF-8";
	private String host;
	private int port = 25;
	private String userName;
	private String pwd;

	// javaMailProperties
	private Properties properties;

	// 来源邮箱地址
	private String from;

	public SendBoxInfo() {
		properties = new Properties();
		properties.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		properties.put("mail.smtp.timeout", "25000");
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getDefaultEncoding() {
		return defaultEncoding;
	}

	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}
}
