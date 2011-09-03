package com.cm4j.taobao.service.goods.items;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import com.cm4j.taobao.TestContext;
import com.cm4j.taobao.exception.ValidationException;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;

public class ItemsServiceTest extends TestContext {

	@Test
	public void testListItems() throws ValidationException, ApiException {
		List<Item> result = ItemsService.listItems("12351649353,12475120363,12858292025", TAOBAO_SESSION_KEY);
		Assert.assertTrue(!CollectionUtils.isEmpty(result));
	}
}
