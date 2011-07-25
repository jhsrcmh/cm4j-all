package com.cm4j.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * 常量基类，所有数据从配置文件中读取
 * <p>子类在开始必需放置如下代码：
 * <pre>
 * static {
 *      init("conf/example.properties");
 * }
 * </pre>
 * </p>
 *
 * @author Sun Xiaochen
 */
public abstract class ConfigurableConstants {
    public static final String DEFAULT_CHARSET_NAME = "UTF-8";
    protected static Properties p = new Properties();
    final static Logger logger = LoggerFactory.getLogger(ConfigurableConstants.class);

    /**
     * 将数据读取到Properties实例中
     * @param propertyFileNames 文件名
     */
   /* protected static void init(String... propertyFileNames) {
        init(DEFAULT_CHARSET_NAME, propertyFileNames);
    }*/

    /**
     * 将数据读取到Properties实例中
     * @param propertyFileNames 文件名
     */
    protected static void init(String... propertyFileNames) {
        if(propertyFileNames != null) {
            for (String propertyFileName : propertyFileNames) {
                InputStream in = null;
                try {
                    in = ConfigurableConstants.class.getClassLoader().getResourceAsStream(propertyFileName);
                    if (in != null) p.load(new InputStreamReader(in, DEFAULT_CHARSET_NAME));
                } catch (Exception e) {
                    logger.warn("load " + propertyFileName + " fail", e);
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            // empty
                        }
                    }
                }
            }
        }
    }

    /**
     * @param key 属性名
     * @return 属性值
     */
    protected static String getValue(String key) {
        return p.getProperty(key);
    }

    /**
     * @param key 属性名
     * @param defaultValue 如果属性不存在，使用该默认值
     * @return 属性值
     */
    protected static String getValue(String key, String defaultValue) {
        return p.getProperty(key, defaultValue);
    }

    /**
     * @param key 属性名
     * @return int类型的属性值，如果没有只返回-1
     */
    protected static long getLongValue(String key) {
        return getLongValue(key, -1);
    }

    /**
     * @param key 属性名
     * @param defaultValue 如果属性不存在，使用该默认值
     * @return int类型的属性值
     */
    protected static long getLongValue(String key, long defaultValue) {
        String value = getValue(key);
        return value == null ? defaultValue : Long.parseLong(value);
    }

    /**
     * @param key 属性名
     * @return boolean类型的属性值，如果属性值不存在，返回false
     */
    protected static boolean getBoolValue(String key) {
        return getBoolValue(key, false);
    }

    /**
     * @param key  属性名
     * @param defaultValue 如果属性不存在，使用该默认值
     * @return boolean类型的属性值
     */
    protected static boolean getBoolValue(String key, boolean defaultValue) {
        String value = getValue(key);
        return value == null ? defaultValue : "true".equals(value);
    }

    /**
     * @param key 属性名
     * @return 值以逗号为间隔，自动转换为字符串数组
     */
    protected static String[] getArrayValue(String key) {
        String value = getValue(key);
        return value == null ? new String[0] : value.split(",");
    }
}