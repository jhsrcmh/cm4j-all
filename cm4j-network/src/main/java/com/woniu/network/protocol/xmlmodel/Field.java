package com.woniu.network.protocol.xmlmodel;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * 
 * @author yang.hao
 * @since 2011-10-27 上午9:21:47
 */
public class Field {
	@XmlAttribute
	String name;
	@XmlAttribute
	String type;
	@XmlAttribute
	String value;

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "name:" + name + ",type:" + type + ",value:" + value;
	}
}
