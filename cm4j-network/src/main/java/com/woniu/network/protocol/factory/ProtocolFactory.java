package com.woniu.network.protocol.factory;

import java.util.concurrent.ConcurrentHashMap;

import org.jboss.netty.buffer.ChannelBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woniu.network.exception.ProtocolParamValidationException;
import com.woniu.network.protocol.ConstructedMessage;
import com.woniu.network.protocol.ProtocolConfig;
import com.woniu.network.protocol.xmlmodel.Header;
import com.woniu.network.protocol.xmlmodel.Message;
import com.woniu.network.protocol.xmlmodel.XMLModel;

/**
 * Protocol工厂类
 * 
 * @author yang.hao
 * @since 2011-10-31 下午4:53:02
 */
public class ProtocolFactory {

	private static final Logger logger = LoggerFactory.getLogger(ProtocolFactory.class);

	public static class Holder {
		public static ProtocolFactory instance = new ProtocolFactory();
	}

	private ConcurrentHashMap<String, ConstructedMessage> keyValue = new ConcurrentHashMap<String, ConstructedMessage>(
			5);

	public ProtocolFactory() {
		try {
			XMLModel xmlModel = ProtocolConfig.getProtocolConfiguration("protocol.xml");
			buildProtocolModel(xmlModel);
		} catch (Exception e) {
			logger.error("xml model translate error", e);
		}
	}

	/**
	 * 从配置文件中创建{@link ConstructedMessage}对象，并放入内存中
	 * 
	 * @param xmlModel
	 */
	private void buildProtocolModel(XMLModel xmlModel) {
		if (xmlModel == null) {
			throw new ProtocolParamValidationException("xmlModel is null");
		}

		Header xmlHeader = xmlModel.getHeader();
		Message[] xmlMessages = xmlModel.getMessages().getMessage();
		for (Message xmlMessage : xmlMessages) {
			ConstructedMessage protocol = new ConstructedMessage(xmlHeader, xmlMessage);

			if (getConstructedMessage(protocol.getMessageId()) != null) {
				throw new ProtocolParamValidationException("the protocol in xml is duplicated,messageId:"
						+ protocol.getMessageId());
			}

			keyValue.putIfAbsent("M-" + protocol.getMessageId(), protocol);
		}
	}

	/**
	 * 获取ConstructedMessage
	 * 
	 * @param messageId
	 * @return
	 */
	public ConstructedMessage getConstructedMessage(int messageId) {
		return keyValue.get("M-" + messageId);
	}

	/**
	 * 获取消息体的messageId
	 * 
	 * @param buffer
	 * @return
	 */
	public int getMessageId(ChannelBuffer buffer) {
		// 从buffer中获取消息体的类型并读取消息
		// 4*6 代表消息头6个int类型参数
		return buffer.getInt(buffer.readerIndex() + 4 * 6);
	}
}
