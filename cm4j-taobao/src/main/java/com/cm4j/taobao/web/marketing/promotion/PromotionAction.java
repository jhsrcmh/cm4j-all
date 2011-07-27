package com.cm4j.taobao.web.marketing.promotion;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
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
		checkRequest(request);
		Object[] result = promotionService.add(request, super.getSessionKey());
	}

	/**
	 * 校验request参数
	 * 
	 * @param request
	 * @throws ValidationException
	 */
	private void checkRequest(MarketingPromotionAddRequest request) throws ValidationException {
		String[] num_ids = StringUtils.split(request.getNumIids(), ",");
		if (num_ids == null || num_ids.length > 10) {
			throw new ValidationException("设置商品个数不允许为0或超过10个");
		}
		if (!ArrayUtils.contains(new String[] { "PRICE", "DISCOUNT" }, request.getDiscountType())) {
			throw new ValidationException("折扣类型不合法");
		}

		if (StringUtils.isBlank(request.getDiscountValue())) {
			throw new ValidationException("优惠额度不能为空");
		} else if ("DISCOUNT".equals(request.getDiscountType())
				&& !request.getDiscountValue().matches("^[1-9](\\.\\d)?$")) {
			// 不满足 1.0-9.9 精确到小数后1位
			throw new ValidationException("折扣比率不合法");
		} else if ("PRICE".equals(request.getDiscountType())) {
			if (!request.getDiscountValue().matches("^(([1-9]\\d+)|([0-9]))(\\.\\d{1,2})?$")) {
				throw new ValidationException("优惠价格不合法");
			}
		}

		if (request.getStartDate() == null || request.getEndDate() == null) {
			throw new ValidationException("优惠开始或结束时间不能为空");
		}

		if (StringUtils.length(request.getPromotionDesc()) > 5) {
			throw new ValidationException("活动标题最长为5个字符");
		}

		if (request.getTagId() == null) {
			throw new ValidationException("用户标签选择不合法");
		}
		
		if (StringUtils.length(request.getPromotionDesc()) > 30) {
			throw new ValidationException("活动描述最长为30个字符");
		}
	}

	public static void main(String[] args) {
		System.out.println("3.1".matches("^[1-9](\\.\\d)?$"));
		System.out.println("0011.1".matches("^\\d+(\\.\\d{1,2})?$"));

		System.out.println(NumberUtils.isNumber(""));
	}
}
