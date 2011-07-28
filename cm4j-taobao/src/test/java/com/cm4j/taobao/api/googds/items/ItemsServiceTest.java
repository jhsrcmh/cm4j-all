package com.cm4j.taobao.api.googds.items;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cm4j.taobao.TestContext;
import com.cm4j.taobao.api.common.APICaller;
import com.taobao.api.ApiException;
import com.taobao.api.request.ItemsOnsaleGetRequest;

public class ItemsServiceTest extends TestContext {

	@Autowired
	private ItemsService itemsService = new ItemsService();

	@Test
	public void onsale_getTest() throws ApiException {
		Map<String, Object> result = itemsService.onsale_get(new ItemsOnsaleGetRequest(),
				TestContext.TAOBAO_SESSION_KEY);
		logger.debug("result:{}", APICaller.jsonBinder.toJson(result));
	}
}
