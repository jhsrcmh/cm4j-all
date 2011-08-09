package com.cm4j.taobao.web.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm4j.taobao.api.shop.ShopAPI;
import com.cm4j.taobao.web.base.BaseDispatchAction;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Shop;

@Controller
@RequestMapping("/secure/shop")
public class ShopAction extends BaseDispatchAction {

	/**
	 * 获取卖家店铺剩余橱窗数量
	 * 
	 * @return 返回例子：{"allCount":15,"remainCount":15,"usedCount":0}
	 * @throws ApiException
	 */
	@RequestMapping("/remainshowcase_get")
	public @ResponseBody
	Shop remainshowcase_get() throws ApiException {
		return ShopAPI.remainshowcase_get(getSessionKey());
	}
}
