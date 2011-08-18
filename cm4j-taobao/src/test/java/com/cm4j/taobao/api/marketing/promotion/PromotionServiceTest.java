package com.cm4j.taobao.api.marketing.promotion;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import com.cm4j.taobao.TestContext;
import com.cm4j.taobao.api.common.APICaller;
import com.cm4j.taobao.api.googds.items.ItemsAPI;
import com.cm4j.taobao.exception.ValidationException;
import com.google.common.base.Joiner;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.request.MarketingPromotionAddRequest;
import com.taobao.api.request.MarketingPromotionUpdateRequest;

public class PromotionServiceTest extends TestContext {

	@SuppressWarnings("unchecked")
	private Long[] getIds() throws ApiException, ValidationException {
		Map<String, Object> onsale_get = ItemsAPI.onsale_get(new ItemsOnsaleGetRequest(), TAOBAO_SESSION_KEY);
		List<Item> onsales = (List<Item>) onsale_get.get("items");
		Long[] ids = new Long[] {};
		for (Item item : onsales) {
			Long numIid = item.getNumIid();
			ids = (Long[]) ArrayUtils.add(ids, numIid);
		}
		return ids;
	}

	@Test
	public void addTest() throws ApiException, ValidationException {
		MarketingPromotionAddRequest request = new MarketingPromotionAddRequest();

		request.setNumIids(Joiner.on(",").join(getIds()));
		request.setDiscountType("PRICE");
		request.setDiscountValue("300.31");
		Date now = new Date();
		request.setStartDate(DateUtils.addHours(now, -1));
		request.setEndDate(DateUtils.addHours(now, 2));
		request.setTagId(1L);
		request.setPromotionTitle("特价促销");
		request.setPromotionDesc("促销描述，促销描述促销描述促销描述促销描述促销描述促销描述");

		Map<String, Object> result = PromotionAPI.add(request, TAOBAO_SESSION_KEY);
		logger.debug("result:{}", APICaller.jsonBinder.toJson(result));
	}

	@Test
	public void updateTest() throws ApiException, ValidationException {
		MarketingPromotionUpdateRequest request = new MarketingPromotionUpdateRequest();

		request.setPromotionId(63928058L);
		request.setDiscountType("PRICE");
		request.setDiscountValue("300.31");
		Date now = new Date();
		request.setStartDate(DateUtils.addHours(now, -1));
		request.setEndDate(DateUtils.addHours(now, 2));
		request.setTagId(1L);
		request.setPromotionTitle("特价2促销");
		// request.setPromotionDesc("促销描述，促销描述促销描述促销描述促销描述促销描述促销描述");

		Map<String, Object> result = PromotionAPI.update(request, TAOBAO_SESSION_KEY);
		logger.debug("result:{}", APICaller.jsonBinder.toJson(result));
	}

	@Test
	public void getTest() throws ApiException, ValidationException {
		Map<String, Object> result = PromotionAPI.get(String.valueOf(getIds()[0]), null, null, 1L, TAOBAO_SESSION_KEY);
		logger.debug("result:{}", APICaller.jsonBinder.toJson(result));
	}

	@Test
	public void deleteTest() throws ApiException {
		boolean is_success = PromotionAPI.delete(75951239L, TAOBAO_SESSION_KEY);
		logger.debug("is_success:{}", APICaller.jsonBinder.toJson(is_success));
	}
}
