package com.cm4j.taobao.web.marketing.promotion;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cm4j.taobao.api.marketing.promotion.PromotionAPI;
import com.cm4j.taobao.exception.ValidationException;
import com.cm4j.taobao.web.base.BaseDispatchAction;
import com.taobao.api.ApiException;
import com.taobao.api.request.MarketingPromotionAddRequest;
import com.taobao.api.request.MarketingPromotionUpdateRequest;

@Controller
@RequestMapping("/secure/promotion")
public class PromotionAction extends BaseDispatchAction {

	/**
	 * 设置商品定向优惠策略
	 * 
	 * @param request
	 * @throws ValidationException
	 * @throws ApiException
	 */
	@RequestMapping("/add")
	public void add(MarketingPromotionAddRequest request) throws ValidationException, ApiException {
		Map<String, Object> result = PromotionAPI.add(request, super.getSessionKey());
		logger.debug("result:{}", result);
	}

	/**
	 * 修改商品定向优惠策略
	 * 
	 * @param request
	 * @throws ValidationException
	 * @throws ApiException
	 */
	@RequestMapping("/update")
	public void update(MarketingPromotionUpdateRequest request) throws ValidationException, ApiException {
		Map<String, Object> result = PromotionAPI.update(request, super.getSessionKey());
		logger.debug("result:{}", result);
	}

	/**
	 * 获取商品定向优惠策略列表
	 * 
	 * @param num_iid
	 *            必须 - 商品数字ID 。根据该ID查询商品下通过第三方工具设置的所有优惠策略
	 * @param fields
	 *            必须 - 需返回的优惠策略结构字段列表，不填则默认显示Promotion所有的字段
	 * @param status
	 *            非必须 - 可选值：ACTIVE(有效)，UNACTIVE(无效)，若不传或者传入其他值，则默认查询全部
	 * @param tag_id
	 *            非必须 - 标签ID
	 * @return
	 * @throws ValidationException
	 * @throws ApiException
	 */
	@RequestMapping("/get/{num_iid}")
	public ModelAndView get(@PathVariable String num_iid, String status, Long tag_id)
			throws ValidationException, ApiException {
		Map<String, Object> result = PromotionAPI.get(num_iid, null, status, tag_id, super.getSessionKey());
		return new ModelAndView("/promotion/list.jsp", result);
	}

}
