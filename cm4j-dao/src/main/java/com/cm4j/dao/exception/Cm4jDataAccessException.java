package com.cm4j.dao.exception;

import org.springframework.dao.DataAccessException;

public class Cm4jDataAccessException extends DataAccessException {

	private static final long serialVersionUID = 1L;

	public Cm4jDataAccessException(String msg) {
		super(msg);
	}
	
	public Cm4jDataAccessException(String msg, Throwable cause) {
		super(msg,cause);
	}
	
}
