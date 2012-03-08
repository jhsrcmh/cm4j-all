package com.woniu.network.protocol;

import java.util.Map.Entry;
import java.util.Set;

import org.jboss.netty.buffer.ChannelBuffer;

public class ProtocolHeader extends ProtocolEntity {

	public ProtocolHeader(int fieldNum) {
		super(fieldNum);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object[] read(ChannelBuffer buffer) {
		logger.info("------解析消息头开始------");
		Set<Entry<String, ProtocolField>> entrySet = super.getProtocolFields().entrySet();
		Object[] result = new Object[super.getProtocolFields().size()];

		for (Entry<String, ProtocolField> entry : entrySet) {
			String key = entry.getKey();
			ProtocolField protocolField = entry.getValue();
			// 消息头
			Object value = protocolField.read(buffer);
			result[protocolField.getFieldPos()] = value;

			logger.info("{}={}[{}]", new Object[] { key, value, protocolField.getFieldType().getName() });
		}

		logger.info("------解析消息头完成------");
		return result;
	}
}
