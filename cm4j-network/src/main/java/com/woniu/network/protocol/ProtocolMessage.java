package com.woniu.network.protocol;

import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.jboss.netty.buffer.ChannelBuffer;

import com.woniu.network.exception.ProtocolTypeDismatchException;
import com.woniu.network.exception.ProtocolValueDismatchException;

/**
 * 消息体
 * 
 * @author yang.hao
 * @since 2011-10-27 上午10:27:36
 */
public class ProtocolMessage extends ProtocolEntity {

	public ProtocolMessage(int fieldNum) {
		super(fieldNum);
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public void rebuild() {
		// 调用父类rebuild()
		super.rebuild();
		// 重新修改Message类，更改argNum的值(不包含argNum本身)
		ProtocolField argNumField = super.getProtocolFields().get("argNum");
		if (argNumField != null) {
			super.intrinsicValues[argNumField.getFieldPos()] = argNumField.getFieldType().decode(
					ObjectUtils.toString(super.getProtocolFields().size() - 1));
		}
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object[] read(ChannelBuffer buffer) {
		logger.info("++++++解析消息体开始++++++");
		Object[] result = new Object[super.getProtocolFields().size()];

		Set<Entry<String, ProtocolField>> entrySet = super.getProtocolFields().entrySet();
		for (Entry<String, ProtocolField> entry : entrySet) {
			String key = entry.getKey();
			ProtocolField protocolField = entry.getValue();

			// 消息体：每个数据前都包含有1个字节的类型信息
			// 类型校验(读取的类型与配置中的类型校验)
			int type = buffer.readByte();
			if (type != protocolField.getFieldType().getType()) {
				throw new ProtocolTypeDismatchException("xml name:" + key + ",type:"
						+ protocolField.getFieldType().toString() + ",actual:" + type);
			}
			// 配置中有值，发送过来的值和配置值不一致，则报错
			Object value = protocolField.read(buffer);
			if (super.intrinsicValues[protocolField.getFieldPos()] != null
					&& !ObjectUtils.toString(value).equals(
							ObjectUtils.toString(super.intrinsicValues[protocolField.getFieldPos()]))) {
				throw new ProtocolValueDismatchException("xml name:" + key + ",value:"
						+ super.intrinsicValues[protocolField.getFieldPos()] + ",actual:" + value);
			}
			result[protocolField.getFieldPos()] = value;

			logger.info("{}={}[{}]", new Object[] { key, value, protocolField.getFieldType().getName() });
		}
		logger.info("++++++解析消息体完成++++++");
		return result;
	}
}
