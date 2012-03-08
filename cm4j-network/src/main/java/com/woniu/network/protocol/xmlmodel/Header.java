package com.woniu.network.protocol.xmlmodel;

import javax.xml.bind.annotation.XmlElement;

import com.woniu.network.protocol.ProtocolEntity;
import com.woniu.network.protocol.ProtocolField;
import com.woniu.network.protocol.ProtocolFieldType;
import com.woniu.network.protocol.ProtocolHeader;

/**
 * 
 * @author yang.hao
 * @since 2011-10-27 上午9:21:43
 */
public class Header {
	@XmlElement
	Field[] field;

	public Field[] getField() {
		return field == null ? new Field[0] : field;
	}

	/**
	 * 根据field创建{@link ProtocolEntity}对象
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ProtocolEntity buildProtocolEntity() {
		// 构建header
		Field[] xmlFields = getField();
		ProtocolEntity entity = newProtocolEntity(xmlFields.length);
		for (int i = 0; i < xmlFields.length; i++) {
			Field field = xmlFields[i];
			// field类型
			ProtocolFieldType protocolFieldType = ProtocolFieldType.getType(field.getType());
			// 设置内在值
			if (field.getValue() != null) {
				entity.getIntrinsicValues()[i] = protocolFieldType.decode(field.getValue());
			}
			// 设置ProtocolField
			ProtocolField protocolField = new ProtocolField(i, protocolFieldType);
			entity.addProtocolField(field.getName(), protocolField);
		}
		return entity;
	}

	protected ProtocolEntity newProtocolEntity(int fieldNum) {
		return new ProtocolHeader(fieldNum);
	}
}
