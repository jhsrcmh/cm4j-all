package com.cm4j.core.utils;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 简单的Jackson封装.
 * @author Sun Xiaochen
 */
public class JsonBinder {
    /**
     * 创建输出全部属性到Json字符串的Binder.
     */
    public static final JsonBinder ALWAYS = new JsonBinder(JsonSerialize.Inclusion.ALWAYS);
    /**
     * 创建只输出非空属性到Json字符串的Binder.
     */
    public static final JsonBinder NON_NULL = new JsonBinder(JsonSerialize.Inclusion.NON_NULL);
    /**
     * 创建只输出初始值被改变的属性到Json字符串的Binder.
     */
    public static final JsonBinder NON_DEFAULT = new JsonBinder(JsonSerialize.Inclusion.NON_DEFAULT);

    private static Logger logger = LoggerFactory.getLogger(JsonBinder.class);
    private ObjectMapper mapper;

    public JsonBinder(JsonSerialize.Inclusion inclusion) {
        mapper = new ObjectMapper();
        //设置输出时包含属性的风格
        mapper.getSerializationConfig().setSerializationInclusion(inclusion);
        // 在java bean中找不到的json属性时不抛异常
        mapper.getDeserializationConfig().set(
                org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 如果JSON字符串为Null或"null"字符串,返回Null.
     * 如果JSON字符串为"[]",返回空集合.
     *
     * 如需读取不是List&lt;String&gt;这种简单类型的集合时使用如下语句:
     * <pre>
     * List&lt;MyBean&gt; beanList = binder.getMapper().readValue(listString, new TypeReference&lt;List&lt;MyBean&gt;&gt;(){});
     * </pre>
     * @param jsonString json字符串
     * @param clazz 目标类
     * @return 如果字符串为空或者json格式错误，返回null
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isNotBlank(jsonString)) {
            try {
                return mapper.readValue(jsonString, clazz);
            } catch (IOException e) {
                logger.warn("read json string error:" + jsonString, e);
            }
        }
        return null;
    }

    /**
	 * 如果对象为Null,返回"null".
	 * 如果集合为空集合,返回"[]".
     * @param object 需要序列号的对象
     * @return json字符串
     */
	public String toJson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			logger.warn("write to json string error:" + object, e);
		}
        return null;
	}
}
