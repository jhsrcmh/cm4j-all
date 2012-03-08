package com.woniu.network.handler.cfg;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class ConfigurableConstants {
	private static Properties p = new Properties();
	final static Logger logger = LoggerFactory.getLogger(ConfigurableConstants.class);

	/**
	 * 将数据读取到Properties实例中
	 * 
	 * @param propertyFileName
	 *            文件名
	 */
	public static void init(String propertyFileName) {
		InputStream in = null;
		try {
			in = ConfigurableConstants.class.getClassLoader().getResourceAsStream(propertyFileName);
			if (in != null)
				p.load(in);
		} catch (IOException e) {
			logger.error("load " + propertyFileName + " into Constants error!", e);
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("close " + propertyFileName + " error!", e);
				}
			}
		}
	}

	/**
	 * @param key
	 *            属性名
	 * @return 属性值
	 */
	public static String getValue(String key) {
		String value = p.getProperty(key);
		if (value == null) {
			logger.warn("'" + key + "' not found!");
		}
		return value;
	}
}