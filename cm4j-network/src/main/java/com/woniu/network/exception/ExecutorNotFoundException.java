package com.woniu.network.exception;

public class ExecutorNotFoundException extends AccessException {

	private static final long serialVersionUID = 1L;

	public ExecutorNotFoundException() {
		super();
	}

	public ExecutorNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExecutorNotFoundException(String message) {
		super(message);
	}

	public ExecutorNotFoundException(Throwable cause) {
		super(cause);
	}

}
