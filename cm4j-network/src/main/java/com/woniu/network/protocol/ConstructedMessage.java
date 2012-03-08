package com.woniu.network.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

import com.woniu.network.protocol.xmlmodel.Header;
import com.woniu.network.protocol.xmlmodel.Message;

/**
 * 构建好的消息协议，包含消息头和消息体
 * 
 * @author yang.hao
 * @since 2011-10-27 上午10:27:45
 */
public class ConstructedMessage{

	private Header xmlHeader;
	private Message xmlMessage;
	
	private ProtocolHeader header;
	/**
	 * 消息头外在值
	 */
	private Object[] headerValues;
	
	private ProtocolMessage message;
	/**
	 * 消息体外在值
	 */
	private Object[] messageValues;

	public ConstructedMessage(Header xmlHeader, Message xmlMessage) {
		this.xmlHeader = xmlHeader;
		this.xmlMessage = xmlMessage;
		
		this.header = (ProtocolHeader) this.xmlHeader.buildProtocolEntity();
		this.message = (ProtocolMessage) this.xmlMessage.buildProtocolEntity();
	}

	public void read(ChannelBuffer buffer) {
		// 读取消息头
		this.headerValues = this.header.read(buffer);
		this.messageValues = this.message.read(buffer);
	}
	
	public void write(ChannelBuffer buffer, Object[] extrinsicValues) {
		if (message != null) {
			// 写入消息头
			this.header.write(buffer, extrinsicValues);
			// 写入消息体
			this.message.write(buffer, extrinsicValues);
		}
	}
	
	public int getMessageId (){
		return (Integer) this.message.intrinsicValues[0];
	}

	public ProtocolHeader getHeader() {
		return header;
	}

	public ProtocolMessage getMessage() {
		return message;
	}
	
	public Object[] getHeaderValues() {
		return headerValues;
	}

	public Object[] getMessageValues() {
		return messageValues;
	}
}
