package com.cm4j.taobao.others;

import java.io.IOException;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taobao.api.request.ItemRecommendAddRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class ConfigurationTest extends AbstractJUnit38SpringContextTests {

	@Resource
	private ObjectMapper customObjectMapper;

	@Test
	public void testJacksonObjectMapper() throws JsonGenerationException, JsonMappingException, IOException {
		Object bean = super.applicationContext.getBean("annotationMethodHandlerAdapter");

		ItemRecommendAddRequest request = new ItemRecommendAddRequest();
		request.setNumIid(100L);
		System.out.println(customObjectMapper.writeValueAsString(request));
	}
}
