package com.cm4j.taobao.api.marketing.promotion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;

import com.cm4j.taobao.api.common.APICaller;
import com.cm4j.taobao.api.common.DomainResolver;
import com.cm4j.taobao.exception.ValidationException;
import com.cm4j.taobao.utils.ValidateUtils;
import com.google.common.base.Joiner;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Promotion;
import com.taobao.api.request.MarketingPromotionAddRequest;
import com.taobao.api.request.MarketingPromotionDeleteRequest;
import com.taobao.api.request.MarketingPromotionUpdateRequest;
import com.taobao.api.request.MarketingPromotionsGetRequest;
import com.taobao.api.response.MarketingPromotionAddResponse;
import com.taobao.api.response.MarketingPromotionDeleteResponse;
import com.taobao.api.response.MarketingPromotionUpdateResponse;
import com.taobao.api.response.MarketingPromotionsGetResponse;

/**
 * API包:taobao.marketing.promotion...
 * 
 * @author yang.hao
 * @since 2011-7-27 下午01:05:45
 * 
 */
@Service
public class PromotionService {

	/**
	 * taobao.marketing.promotion.add 设置商品定向优惠策略
	 * 
	 * @param request
	 *            注意：如果是PRICE折扣，DiscountValue代表的是减去的优惠价格，而不是折扣以后价格
	 * @param sessionKey
	 * @return is_success - 必须，是否成功<br />
	 *         promotions - 必须，设置成功的优惠信息(仅返回Promotion数据结构中的promotion_id,item_id,
	 *         item_detail_url)
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public Map<String, Object> add(MarketingPromotionAddRequest request, String sessionKey) throws ApiException,
			ValidationException {
		checkAddRequest(request);

		MarketingPromotionAddResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("is_success", response.getIsSuccess());
		result.put("promotions", response.getPromotions());

		return result;
	}

	/**
	 * taobao.marketing.promotion.update 更新商品定向优惠策略<br />
	 * 
	 * 注意：对于promotion_desc, promotion_title, decrease_num
	 * 这3个可选项，如果不填值默认是未更新前的值，其他都是比填值
	 * 
	 * @param request
	 * @param sessionKey
	 * @return is_success - 必须，是否成功<br />
	 *         promotions - 必须，更新的优惠对象
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public Map<String, Object> update(MarketingPromotionUpdateRequest request, String sessionKey) throws ApiException,
			ValidationException {
		checkUpdateRequest(request);

		MarketingPromotionUpdateResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("is_success", response.getIsSuccess());
		result.put("promotion", response.getPromotion());

		return result;
	}

	/**
	 * taobao.marketing.promotions.get 查询商品定向优惠策略
	 * 
	 * 
	 * @param num_iid
	 *            必须 - 商品数字ID 。根据该ID查询商品下通过第三方工具设置的所有优惠策略
	 * @param fields
	 *            必须 - 需返回的优惠策略结构字段列表，不填则默认显示Promotion所有的字段
	 * @param status
	 *            非必须 - 可选值：ACTIVE(有效)，UNACTIVE(无效)，若不传或者传入其他值，则默认查询全部
	 * @param tag_id
	 *            非必须 - 标签ID
	 * @param sessionKey
	 * @return total_results - 非必须，结果总数<br/>
	 *         promotions - 必须，商品对应的所有优惠列表
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public Map<String, Object> get(String num_iid, String fields, String status, Long tag_id, String sessionKey)
			throws ApiException, ValidationException {

		if (StringUtils.isBlank(num_iid) || !NumberUtils.isNumber(num_iid)) {
			throw new ValidationException("商品数字ID不合法");
		}

		MarketingPromotionsGetRequest request = new MarketingPromotionsGetRequest();
		request.setNumIid(num_iid);
		if (StringUtils.isNotBlank(fields)) {
			request.setFields(fields);
		} else {
			List<String> values = DomainResolver.getApiFieldValues(Promotion.class);
			String joinedFields = Joiner.on(",").join(values);
			request.setFields(joinedFields);
		}
		request.setStatus(status);
		request.setTagId(tag_id);

		MarketingPromotionsGetResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total_results", response.getTotalResults());
		result.put("promotions", response.getPromotions());

		return result;
	}

	/**
	 * taobao.marketing.promotion.delete 删除商品定向优惠策略<br/>
	 * 注意：这里其实只是把状态改成了UNACTIVE，并没有真正删记录
	 * 
	 * @param promotion_id
	 *            已设置的优惠策略ID
	 * @return 是否成功
	 * @throws ApiException
	 */
	public boolean delete(long promotion_id, String sessionKey) throws ApiException {
		MarketingPromotionDeleteRequest request = new MarketingPromotionDeleteRequest();
		request.setPromotionId(promotion_id);

		MarketingPromotionDeleteResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);

		return response.getIsSuccess();
	}

	/**
	 * 校验AddRequest参数 <br />
	 * <b>注意：商品折扣价不能超过原价，这里由于是多个商品的促销活动，无法限制，故建议前台限制</b>
	 * 
	 * @param request
	 * @throws ValidationException
	 */
	private void checkAddRequest(MarketingPromotionAddRequest request) throws ValidationException {
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
				&& !ValidateUtils.validateDiscount(request.getDiscountValue())) {
			// 不满足 1.0-9.9 精确到小数后1位
			throw new ValidationException("折扣比率不合法");
		} else if ("PRICE".equals(request.getDiscountType())) {
			if (!ValidateUtils.validateDecimal(request.getDiscountValue())) {
				throw new ValidationException("优惠价格不合法");
			}
		}

		if (request.getStartDate() == null || request.getEndDate() == null) {
			throw new ValidationException("优惠活动开始或结束时间不能为空");
		}

		if (StringUtils.length(request.getPromotionTitle()) > 5) {
			throw new ValidationException("活动标题最长为5个字符");
		}

		if (request.getTagId() == null) {
			throw new ValidationException("用户标签选择不合法");
		}

		if (StringUtils.length(request.getPromotionDesc()) > 30) {
			throw new ValidationException("活动描述最长为30个字符");
		}
	}

	/**
	 * 校验UpdateRequest参数 <br />
	 * 
	 * @param request
	 * @throws ValidationException
	 */
	private void checkUpdateRequest(MarketingPromotionUpdateRequest request) throws ValidationException {
		if (request.getPromotionId() == null) {
			throw new ValidationException("优惠活动ID不能为空");
		}
		if (!ArrayUtils.contains(new String[] { "PRICE", "DISCOUNT" }, request.getDiscountType())) {
			throw new ValidationException("折扣类型不合法");
		}

		if (StringUtils.isBlank(request.getDiscountValue())) {
			throw new ValidationException("优惠额度不能为空");
		} else if ("DISCOUNT".equals(request.getDiscountType())
				&& !ValidateUtils.validateDiscount(request.getDiscountValue())) {
			// 不满足 1.0-9.9 精确到小数后1位
			throw new ValidationException("折扣比率不合法");
		} else if ("PRICE".equals(request.getDiscountType())) {
			if (!ValidateUtils.validateDecimal(request.getDiscountValue())) {
				throw new ValidationException("优惠价格不合法");
			}
		}

		if (request.getStartDate() == null || request.getEndDate() == null) {
			throw new ValidationException("优惠活动开始或结束时间不能为空");
		}

		if (StringUtils.length(request.getPromotionTitle()) > 5) {
			throw new ValidationException("活动标题最长为5个字符");
		}

		if (request.getTagId() == null) {
			throw new ValidationException("用户标签选择不合法");
		}

		if (StringUtils.length(request.getPromotionDesc()) > 30) {
			throw new ValidationException("活动描述最长为30个字符");
		}
	}
}
