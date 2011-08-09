package com.cm4j.taobao.web.base;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.FactoryBean;

import com.taobao.api.request.ItemAddRequest;

public class JacksonObjectMapper implements FactoryBean<ObjectMapper> {

	@Override
	public ObjectMapper getObject() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		// 设置输出时包含属性的风格
		mapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
		// 在java bean中找不到的json属性时不抛异常
		mapper.getDeserializationConfig().set(
				org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	@Override
	public Class<?> getObjectType() {
		return ObjectMapper.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException, Exception {
		JacksonObjectMapper manager = new JacksonObjectMapper();
		ItemAddRequest request = new ItemAddRequest();
		request.setAfterSaleId(10L);
//		System.out.println(APICaller.jsonBinder.toJson(request));
		System.out.println(manager.getObject().writeValueAsString(request));
	}
}
