package com.cm4j.taobao.api.googds.items;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cm4j.core.utils.JsonBinder;
import com.cm4j.taobao.TestContext;
import com.taobao.api.ApiException;
import com.taobao.api.request.ItemsOnsaleGetRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class ItemsServiceTest {

	public final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ItemsService itemsService;

	@Test
	public void onsale_getTest() throws ApiException {
		Object[] result = itemsService.onsale_get(new ItemsOnsaleGetRequest(), TestContext.TAOBAO_SESSION_KEY);
		logger.debug("result:{}", JsonBinder.NON_NULL.toJson(result));
	}
}
