package com.woniu.network.protocol;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息头和消息体父类<br>
 * 内部维护了一个map键值对，键和配置中键一致，值为{@link ProtocolField}
 * 
 * @author yang.hao
 * @since 2011-10-26 下午4:54:29
 */
@SuppressWarnings("rawtypes")
public abstract class ProtocolEntity {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private LinkedHashMap<String, ProtocolField> fields;
	// 内部状态 - 初始值，禁止修改
	protected final Object[] intrinsicValues;

	public ProtocolEntity(int fieldNum) {
		intrinsicValues = new Object[fieldNum];
		this.fields = new LinkedHashMap<String, ProtocolField>(fieldNum);
	}

	public void addProtocolField(String fieldName, ProtocolField field) {
		fields.put(fieldName, field);
	}

	public Map<String, ProtocolField> getProtocolFields() {
		return fields;
	}

	public ProtocolField getProtocolField(String fieldName) {
		return fields.get(fieldName);
	}

	/**
	 * 获取字段值
	 * 
	 * @param fieldName
	 * @return
	 */
	public Object getProtocolFieldValue(String fieldName) {
		return intrinsicValues[getProtocolField(fieldName).getFieldPos()];
	}

	/**
	 * 将对象写入{@link ChannelBuffer}
	 * 
	 * @param buffer
	 * @param extrinsicValues
	 */
	@SuppressWarnings("unchecked")
	public void write(ChannelBuffer buffer, Object[] extrinsicValues) {
		// 写入前先进行重置参数，如argNum等，子类覆盖
		rebuild();

		Collection<ProtocolField> _fieldValues = fields.values();
		boolean is_body = this instanceof ProtocolMessage;
		for (ProtocolField protocolField : _fieldValues) {
			// 优先写入外部值，无外部值，则写入内部值
			protocolField.write(buffer, is_body,
					extrinsicValues[protocolField.getFieldPos()] == null ? intrinsicValues[protocolField.getFieldPos()]
							: extrinsicValues[protocolField.getFieldPos()]);
		}
	}

	/**
	 * 从{@link ChannelBuffer}获取值并拼装为对象
	 * 
	 * @param buffer
	 * @return
	 */
	public abstract Object[] read(ChannelBuffer buffer);

	/**
	 * 重新构造当前类，需要子类覆盖<br>
	 * 比如对自定义消息argNum的值的修改
	 */
	protected void rebuild() {
		// todo entity加载时默认就设置了默认值
		// 将值为空的字段值替换为其类型默认值
		// Collection<ProtocolField> _fieldValues = fields.values();
		// for (ProtocolField protocolField : _fieldValues) {
		// if (protocolField.getFieldValue() == null) {
		// protocolField.setDefaultValue();
		// }
		// }
	}

	public Object[] getIntrinsicValues() {
		return intrinsicValues;
	}
}
