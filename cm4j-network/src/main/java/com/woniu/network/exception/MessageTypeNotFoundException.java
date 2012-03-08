package com.woniu.network.exception;

/**
 * 异常：消息类型未查询到
 * 
 * @author yang.hao
 * @since 2011-10-31 上午11:04:31
 */
public class MessageTypeNotFoundException extends AccessException {
	private static final long serialVersionUID = 1L;

	public MessageTypeNotFoundException() {
		super();
	}

	public MessageTypeNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageTypeNotFoundException(String message) {
		super(message);
	}

	public MessageTypeNotFoundException(Throwable cause) {
		super(cause);
	}

}
