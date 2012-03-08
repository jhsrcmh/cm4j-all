package com.woniu.network.protocol;

import org.jboss.netty.buffer.ChannelBuffer;

/**
 * 协议内字段
 * 
 * @author yang.hao
 * @since 2011-10-26 下午2:21:07
 */
public class ProtocolField<T> {

	// 字段在配置中的位置，从0开始
	private int fieldPos;
	// 字段类型
	private ProtocolFieldType<T> fieldType;

	public ProtocolField(int fieldPos, ProtocolFieldType<T> fieldType) {
		this.fieldPos = fieldPos;
		this.fieldType = fieldType;
	}

	/**
	 * 写入数据到buffer中
	 * 
	 * @param buffer
	 * @param addTypeInfo
	 *            是否在实际值之前写入1个字节的类型信息
	 * @throws IndexOutOfBoundsException
	 */
	public void write(ChannelBuffer buffer, boolean addTypeInfo,T fieldValue) {
		if (addTypeInfo) {
			buffer.writeByte(fieldType.getType());
		}
		this.fieldType.put(buffer, fieldValue);
	}

	/**
	 * 从buffer中读取数据
	 * 
	 * @param buffer
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public T read(ChannelBuffer buffer) {
		return this.fieldType.get(buffer);
	}

	public ProtocolFieldType<?> getFieldType() {
		return fieldType;
	}
	
	public int getFieldPos() {
		return fieldPos;
	}
	
	@Override
	public String toString() {
		return "[fieldPos:" + fieldPos + ",fieldType:" + fieldType.toString() + "]";
	}
}
