package com.cm4j.taobao.api.googds.items;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.cm4j.taobao.TestContext;
import com.cm4j.taobao.api.common.APICaller;
import com.cm4j.taobao.exception.ValidationException;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemsOnsaleGetRequest;

public class ItemsAPITest extends TestContext {

	@Test
	public void onsale_getTest() throws ApiException, ValidationException {
		Map<String, Object> result = ItemsAPI.onsale_get(new ItemsOnsaleGetRequest(), TAOBAO_SESSION_KEY);
		logger.debug("taobao.items.onsale.get:{}", APICaller.jsonBinder.toJson(result));
	}

	@Test
	public void list_get() throws ValidationException, ApiException {
		List<Item> result = ItemsAPI.list_get(null, "12351649353,12475120363", TAOBAO_SESSION_KEY);
		logger.debug("taobao.items.list.get:{}", APICaller.jsonBinder.toJson(result));
	}
}
