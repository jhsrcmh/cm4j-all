package com.cm4j.taobao.api.shop;

import org.junit.Test;

import com.cm4j.taobao.TestContext;
import com.cm4j.taobao.api.common.APICaller;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Shop;

public class ShopAPITest extends TestContext {

	/**
	 * 测试获取商家剩余橱窗推荐数
	 * 
	 * @throws ApiException
	 */
	@Test
	public void remainshowcase_getTest() throws ApiException {
		Shop shop = ShopAPI.remainshowcase_get(TAOBAO_SESSION_KEY);
		logger.debug("taobao.shop.remainshowcase.get:{}", APICaller.jsonBinder.toJson(shop));
	}
}
