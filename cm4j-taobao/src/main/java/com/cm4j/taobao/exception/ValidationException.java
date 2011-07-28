package com.cm4j.taobao.exception;

/**
 * 校验类异常
 * 
 * @author yang.hao
 * @since 2011-7-28 下午04:43:17
 *
 */
public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	public ValidationException() {
		super();
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}

}
