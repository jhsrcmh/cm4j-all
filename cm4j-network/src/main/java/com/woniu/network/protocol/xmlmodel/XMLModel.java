package com.woniu.network.protocol.xmlmodel;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 与xml对应的消息模型
 * 
 * @author yang.hao
 * @since 2011-10-27 上午9:21:31
 */
@XmlRootElement(name = "protocol")
public class XMLModel {
	@XmlElement
	Header header;
	@XmlElement
	Messages messages;
	@XmlAttribute(name = "little-endian")
	Boolean littleEndian;
	@XmlAttribute(name = "string-encoding")
	String stringEncoding;
	@XmlAttribute(name = "wstring-encoding")
	String wstringEncoding;

	public Header getHeader() {
		return header;
	}

	public Messages getMessages() {
		return messages;
	}

	public Boolean getLittleEndian() {
		return littleEndian;
	}

	public String getStringEncoding() {
		return stringEncoding;
	}

	public String getWstringEncoding() {
		return wstringEncoding;
	}
}
