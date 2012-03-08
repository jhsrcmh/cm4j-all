package com.woniu.network.exception;

/**
 * 异常：读取数据的协议值与配置中的类型不一致
 * 
 * @author yang.hao
 * @since 2011-10-26 下午4:52:04
 */
public class ProtocolValueDismatchException extends AccessException {

	private static final long serialVersionUID = 1L;

	public ProtocolValueDismatchException() {
		super();
	}

	public ProtocolValueDismatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProtocolValueDismatchException(String message) {
		super(message);
	}

	public ProtocolValueDismatchException(Throwable cause) {
		super(cause);
	}

}
