package com.cm4j.taobao.api.shop;

import org.springframework.stereotype.Service;

import com.cm4j.taobao.api.common.APICaller;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Shop;
import com.taobao.api.request.ShopRemainshowcaseGetRequest;
import com.taobao.api.response.ShopRemainshowcaseGetResponse;

/**
 * 店铺API包：taobao.shop... taobao.picture...等等
 * 
 * @author yang.hao
 * @since 2011-8-3 上午10:25:08
 * 
 */
@Service
public class ShopAPI {

	/**
	 * taobao.shop.remainshowcase.get 获取卖家店铺剩余橱窗数量
	 * 
	 * @param sessionKey
	 * @return 返回例子：{"allCount":15,"remainCount":15,"usedCount":0}
	 * @throws ApiException
	 */
	public static Shop remainshowcase_get(String sessionKey) throws ApiException {
		ShopRemainshowcaseGetResponse response = APICaller.call(new ShopRemainshowcaseGetRequest(), sessionKey);
		APICaller.resolveResponseException(response);
		return response.getShop();
	}
}
