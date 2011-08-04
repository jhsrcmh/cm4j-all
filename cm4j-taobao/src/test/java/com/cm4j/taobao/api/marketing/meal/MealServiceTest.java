package com.cm4j.taobao.api.marketing.meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.cm4j.taobao.TestContext;
import com.cm4j.taobao.api.common.APICaller;
import com.cm4j.taobao.api.googds.items.ItemAPI;
import com.cm4j.taobao.api.marketing.meal.MealAPI.SimpleItemList;
import com.cm4j.taobao.api.marketing.meal.MealAPI.SimpleItemList.SimpleItem;
import com.cm4j.taobao.exception.ValidationException;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.request.PromotionMealAddRequest;

public class MealServiceTest extends TestContext {

	@Test
	public void addTest() throws ApiException, ValidationException {
		PromotionMealAddRequest request = new PromotionMealAddRequest();
		request.setMealName("套餐齐推");
		request.setMealMemo("套餐齐推简介套餐齐推简介套餐齐推简介套餐齐推简介");
		request.setMealPrice("600.05");
		request.setTypePostage("SELL");

		SimpleItemList list = new SimpleItemList();
		
		List<SimpleItem> simpleItems = new ArrayList<SimpleItem>();
		Item item_1 = ItemAPI.get(12351649353L, null, TAOBAO_SESSION_KEY);
		Item item_2 = ItemAPI.get(12475120363L, null, TAOBAO_SESSION_KEY);
		simpleItems.add(new SimpleItem(String.valueOf(item_1.getNumIid()), item_1.getTitle()));
		simpleItems.add(new SimpleItem(String.valueOf(item_2.getNumIid()), item_2.getTitle()));
		list.setItem_list(simpleItems);
		
		request.setItemList(APICaller.jsonBinder.toJson(list));

		Map<String, Object> result = MealAPI.add(request, TAOBAO_SESSION_KEY);
		logger.debug("taobao.promotion.meal.add:{}", APICaller.jsonBinder.toJson(result));
	}
}
