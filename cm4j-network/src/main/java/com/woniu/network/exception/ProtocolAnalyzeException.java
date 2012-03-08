package com.woniu.network.exception;

import org.jboss.netty.buffer.ChannelBuffer;

import com.woniu.network.protocol.ConstructedMessage;

/**
 * 协议解析或泛解析异常异常<br>
 * {@link ChannelBuffer} <==> {@link ConstructedMessage}
 * 
 * @author yang.hao
 * @since 2011-11-3 下午5:29:13
 */
public class ProtocolAnalyzeException extends AccessException {

	private static final long serialVersionUID = 1L;

	public ProtocolAnalyzeException() {
		super();
	}

	public ProtocolAnalyzeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProtocolAnalyzeException(String message) {
		super(message);
	}

	public ProtocolAnalyzeException(Throwable cause) {
		super(cause);
	}

}
