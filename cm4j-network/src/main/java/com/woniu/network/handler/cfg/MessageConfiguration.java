package com.woniu.network.handler.cfg;

import org.apache.commons.lang.math.NumberUtils;

public class MessageConfiguration extends ConfigurableConstants {

	{
		init("network.properties");
	}

	public static String getServerHost() {
		return getValue("server.host");
	}

	public static int getServerPort() {
		return NumberUtils.toInt(getValue("server.port"));
	}
}
