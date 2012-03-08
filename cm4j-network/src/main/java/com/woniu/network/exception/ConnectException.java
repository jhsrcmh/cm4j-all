package com.woniu.network.exception;

/**
 * 连接异常
 * 
 * @author yang.hao
 * @since 2011-11-23 上午10:54:00
 */
public class ConnectException extends AccessException {

	private static final long serialVersionUID = 1L;

	public ConnectException() {
		super();
	}

	public ConnectException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectException(String message) {
		super(message);
	}

	public ConnectException(Throwable cause) {
		super(cause);
	}

}
