package com.cm4j.taobao.api.marketing.tags;

import java.util.List;

import org.junit.Test;

import com.cm4j.taobao.TestContext;
import com.taobao.api.ApiException;
import com.taobao.api.domain.UserTag;

public class TagsServiceTest extends TestContext {

	private TagsService tagsService = new TagsService();

	@Test
	public void getTest() throws ApiException {
		List<UserTag> userTags = tagsService.get(TestContext.TAOBAO_SESSION_KEY);
		logger.debug("result:{}", userTags);
	}
}
