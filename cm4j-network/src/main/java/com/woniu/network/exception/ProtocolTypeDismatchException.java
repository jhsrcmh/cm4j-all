package com.woniu.network.exception;

/**
 * 异常：读取数据的协议类型与配置中的类型不一致
 * 
 * @author yang.hao
 * @since 2011-10-26 下午4:52:04
 */
public class ProtocolTypeDismatchException extends AccessException {

	private static final long serialVersionUID = 1L;

	public ProtocolTypeDismatchException() {
		super();
	}

	public ProtocolTypeDismatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProtocolTypeDismatchException(String message) {
		super(message);
	}

	public ProtocolTypeDismatchException(Throwable cause) {
		super(cause);
	}

}
