package com.cm4j.taobao.web.marketing.promotion;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cm4j.taobao.api.marketing.promotion.PromotionService;
import com.cm4j.taobao.exception.ValidationException;
import com.cm4j.taobao.web.base.BaseDispatchAction;
import com.taobao.api.ApiException;
import com.taobao.api.request.MarketingPromotionAddRequest;

@Controller
@RequestMapping("/safe/marketing/promotion")
public class PromotionAction extends BaseDispatchAction {

	@Autowired
	private PromotionService promotionService;

	/**
	 * 设置商品定向优惠策略
	 * 
	 * @param num_iids
	 * @param discount_type
	 * @param discount_value
	 * @param start_date
	 * @param end_date
	 * @param promotion_title
	 * @param tag_id
	 * @param promotion_desc
	 * @throws ApiException
	 */
	@RequestMapping("/add")
	public void add(MarketingPromotionAddRequest request) throws ValidationException, ApiException {
		Map<String, Object> result = promotionService.add(request, super.getSessionKey());
		logger.debug("result:{}", result);
	}
}
