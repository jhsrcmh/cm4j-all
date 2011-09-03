package com.cm4j.taobao.service.goods.items;

import java.util.List;

import com.cm4j.taobao.api.goods.items.ItemsAPI;
import com.cm4j.taobao.exception.ValidationException;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;

public class ItemsService {

	/**
	 * 根据ID批量查询商品信息
	 * 
	 * @param num_iids
	 * @param sessionKey
	 * @return Item，包含内容 ID，标题，价格，商品url，商品主图片地址，下架时间
	 * @throws ValidationException
	 * @throws ApiException
	 */
	public static List<Item> listItems(String num_iids, String sessionKey) throws ValidationException, ApiException {
		// ID，标题，价格，商品url，商品主图片地址
		return ItemsAPI.list_get("num_iid,title,price,detail_url,pic_url,delist_time", num_iids, sessionKey);
	}
}
