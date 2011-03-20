package com.cm4j.email.sender;

/**
 * 发送内容
 * 
 * @author Administrator
 * 
 */
public class EmailContent {

	private String subject;
	private String content;
	// 发送的是否为html
	private boolean html;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isHtml() {
		return html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}
}
