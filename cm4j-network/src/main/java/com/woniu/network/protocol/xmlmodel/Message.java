package com.woniu.network.protocol.xmlmodel;

import javax.xml.bind.annotation.XmlAttribute;

import com.woniu.network.protocol.ProtocolEntity;
import com.woniu.network.protocol.ProtocolMessage;

/**
 * 
 * @author yang.hao
 * @since 2011-10-27 上午9:20:57
 */
public class Message extends Header {
	@XmlAttribute
	String id;

	public String getId() {
		return id;
	}

	@Override
	protected ProtocolEntity newProtocolEntity(int fieldNum) {
		return new ProtocolMessage(fieldNum);
	}

}