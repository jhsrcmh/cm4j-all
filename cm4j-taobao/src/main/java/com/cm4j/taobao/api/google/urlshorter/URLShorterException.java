package com.cm4j.taobao.api.google.urlshorter;

/**
 * http异常
 * 
 * @author yang.hao
 * @since 2011-8-31 下午9:12:06
 */
public class URLShorterException extends Exception {
	private static final long serialVersionUID = 1L;

	public URLShorterException(String message, Throwable cause) {
		super(message, cause);
	}

	public URLShorterException(String message) {
		super(message);
	}

	public URLShorterException(Throwable cause) {
		super(cause);
	}
}