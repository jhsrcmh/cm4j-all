package com.cm4j.taobao.api.marketing.meal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.cm4j.taobao.api.common.APICaller;
import com.cm4j.taobao.exception.ValidationException;
import com.cm4j.taobao.utils.ValidateUtils;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Meal;
import com.taobao.api.request.PromotionMealAddRequest;
import com.taobao.api.request.PromotionMealDeleteRequest;
import com.taobao.api.request.PromotionMealGetRequest;
import com.taobao.api.request.PromotionMealUpdateRequest;
import com.taobao.api.response.PromotionMealAddResponse;
import com.taobao.api.response.PromotionMealDeleteResponse;
import com.taobao.api.response.PromotionMealGetResponse;
import com.taobao.api.response.PromotionMealUpdateResponse;

/**
 * API包:taobao.marketing.promotion...
 * 
 * @author yang.hao
 * @since 2011-7-28 下午03:53:55
 * 
 */
public class MealService {

	/**
	 * 简化版item_list，用于add调用方法
	 * 
	 * @author yang.hao
	 * @since 2011-7-28 下午04:52:20
	 * 
	 */
	public static class SimpleItemList {
		private List<SimpleItem> item_list;

		public List<SimpleItem> getItem_list() {
			return item_list;
		}

		public void setItem_list(List<SimpleItem> item_list) {
			this.item_list = item_list;
		}

		public static class SimpleItem {
			private String item_id;
			private String item_show_name;

			public SimpleItem() {
				super();
			}

			public SimpleItem(String item_id, String item_show_name) {
				super();
				this.item_id = item_id;
				this.item_show_name = item_show_name;
			}

			public String getItem_id() {
				return item_id;
			}

			public void setItem_id(String item_id) {
				this.item_id = item_id;
			}

			public String getItem_show_name() {
				return item_show_name;
			}

			public void setItem_show_name(String item_show_name) {
				this.item_show_name = item_show_name;
			}
		}
	}

	/**
	 * taobao.promotion.meal.add 新增搭配套餐<br />
	 * 
	 * @param request
	 *            注意：meal_price，搭配套餐一口价。这个值要大于0.01，小于商品的价值总和。 这个需要做校验
	 * @param sessionKey
	 * @return is_success - 必须，true：成功 false：失败<br />
	 *         meal_id - 必须，搭配套餐id。<br />
	 *         modify_time - 必须，创建时间。
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public Map<String, Object> add(PromotionMealAddRequest request, String sessionKey) throws ApiException,
			ValidationException {

		checkAddRequest(request);

		PromotionMealAddResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("is_success", response.getIsSuccess());
		result.put("meal_id", response.getMealId());
		result.put("modify_time", response.getModifyTime());

		return result;
	}

	/**
	 * taobao.promotion.meal.update 搭配套餐修改
	 * 
	 * @param request
	 * @param sessionKey
	 * @return is_success - 必须，true：成功 false：失败<br />
	 *         modify_time - 必须，创建时间。
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public Map<String, Object> update(PromotionMealUpdateRequest request, String sessionKey) throws ApiException,
			ValidationException {
		checkUpdateRequest(request);

		PromotionMealUpdateResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("is_success", response.getIsSuccess());
		result.put("modify_time", response.getModifyTime());

		return result;
	}

	/**
	 * taobao.promotion.meal.delete 搭配套餐删除
	 * 
	 * @param meal_id
	 *            搭配套餐ID
	 * @return is_success - 必须，是否成功 true：成功 false：失败<br />
	 *         modify_time - 必须，修改时间。
	 * @throws ApiException
	 */
	public Map<String, Object> delete(long meal_id, String sessionKey) throws ApiException {
		PromotionMealDeleteRequest request = new PromotionMealDeleteRequest();
		request.setMealId(meal_id);

		PromotionMealDeleteResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("is_success", response.getIsSuccess());
		result.put("modify_time", response.getModifyTime());

		return result;
	}

	/**
	 * taobao.promotion.meal.get 搭配套餐查询
	 * 
	 * @param meal_id
	 *            非必须，搭配套餐ID
	 * @param status
	 *            非必须，有效：VALID;失效：INVALID(有效套餐为可使用的套餐,无效套餐为套餐中有商品下架或库存为0时)。
	 *            默认时两种情况都会查询。
	 * @return
	 * @throws ApiException
	 */
	public List<Meal> get(Long meal_id, String status, String sessionKey) throws ApiException {
		PromotionMealGetRequest request = new PromotionMealGetRequest();
		request.setMealId(meal_id);
		request.setStatus(status);

		PromotionMealGetResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);

		return response.getMealList();
	}

	private void checkAddRequest(PromotionMealAddRequest request) throws ValidationException {
		int mealNameLength = StringUtils.length(request.getMealName());
		if (mealNameLength <= 0 || mealNameLength > 30) {
			throw new ValidationException("搭配套餐名称长度必须为1-30位");
		}

		if (!ValidateUtils.validateDecimal(request.getMealPrice(), 2)) {
			throw new ValidationException("搭配套餐一口价格式不合法，精确到小数后两位");
		}

		int mealMemoLength = StringUtils.length(request.getMealMemo());
		if (mealMemoLength <= 0 || mealMemoLength > 30) {
			throw new ValidationException("搭配套餐描述长度必须为1-30位");
		}

		SimpleItemList item_list = APICaller.jsonBinder.fromJson(request.getItemList(), SimpleItemList.class);
		if (item_list == null || item_list.getItem_list() == null) {
			throw new ValidationException("搭配套餐商品列表不合法");
		}
		int itemSize = item_list.getItem_list().size();
		if (itemSize < 2 || itemSize > 5) {
			throw new ValidationException("搭配套餐商品列表数量范围为2-5个");
		}

		// 运费模板类型。卖家标识'SELL';买家标识'BUY'。
		// 若为'SELL',则字段postage_id忽略。若为'BUY'，则postage_id为运费模板id，为必添项。
		if (!ArrayUtils.contains(new String[] { "SELL", "BUY" }, request.getTypePostage())) {
			throw new ValidationException("运费模板类型必须是SELL或BUY");
		}

		// 普通运费模板id。商品API:taobao.postages.get获取卖家的运费模板。
		// 当type_postage为BUY时，为必添项。当type_postage为SELL时，此字段忽略
		if ("BUY".equals(request.getTypePostage()) && request.getPostageId() == null) {
			throw new ValidationException("买家运费模板不允许为空");
		}
	}

	private void checkUpdateRequest(PromotionMealUpdateRequest request) throws ValidationException {
		if (request.getMealId() == null) {
			throw new ValidationException("搭配套餐ID不允许为空");
		}

		int mealNameLength = StringUtils.length(request.getMealName());
		if (mealNameLength <= 0 || mealNameLength > 30) {
			throw new ValidationException("搭配套餐名称长度必须为1-30位");
		}

		if (!ValidateUtils.validateDecimal(request.getMealPrice(), 2)) {
			throw new ValidationException("搭配套餐一口价格式不合法，精确到小数后两位");
		}

		int mealMemoLength = StringUtils.length(request.getMealMemo());
		if (mealMemoLength <= 0 || mealMemoLength > 30) {
			throw new ValidationException("搭配套餐描述长度必须为1-30位");
		}

		SimpleItemList item_list = APICaller.jsonBinder.fromJson(request.getItemList(), SimpleItemList.class);
		if (item_list == null || item_list.getItem_list() == null) {
			throw new ValidationException("搭配套餐商品列表不合法");
		}
		int itemSize = item_list.getItem_list().size();
		if (itemSize < 2 || itemSize > 5) {
			throw new ValidationException("搭配套餐商品列表数量范围为2-5个");
		}

		// 运费模板类型。卖家标识'SELL';买家标识'BUY'。
		// 若为'SELL',则字段postage_id忽略。若为'BUY'，则postage_id为运费模板id，为必添项。
		if (!ArrayUtils.contains(new String[] { "SELL", "BUY" }, request.getTypePostage())) {
			throw new ValidationException("运费模板类型必须是SELL或BUY");
		}

		// 普通运费模板id。商品API:taobao.postages.get获取卖家的运费模板。
		// 当type_postage为BUY时，为必添项。当type_postage为SELL时，此字段忽略
		if ("BUY".equals(request.getTypePostage()) && request.getPostageId() == null) {
			throw new ValidationException("买家运费模板不允许为空");
		}
	}
}
