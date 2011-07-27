package com.cm4j.taobao.api.marketing.tags;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cm4j.taobao.TestContext;
import com.taobao.api.ApiException;
import com.taobao.api.domain.UserTag;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class TagsServiceTest {

	public final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TagsService tagsService;

	@Test
	public void getTest() throws ApiException {
		List<UserTag> userTags = tagsService.get(TestContext.TAOBAO_SESSION_KEY);
		logger.debug("result:{}", userTags);
	}
}
