package com.cm4j.taobao.api.goods.items;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cm4j.taobao.api.common.APICaller;
import com.cm4j.taobao.exception.ValidationException;
import com.taobao.api.ApiException;
import com.taobao.api.request.ItemsInventoryGetRequest;
import com.taobao.api.response.ItemsInventoryGetResponse;

/**
 * 商品API包：taobao.items
 * 
 * @author yang.hao
 * @since 2011-8-8 下午04:06:13
 * 
 */
public class InventoryAPI {

	/**
	 * taobao.items.inventory.get 得到当前会话用户库存中的商品列表
	 * 
	 * @param request
	 * @param sessionKey
	 * @return
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public static Map<String, Object> get(ItemsInventoryGetRequest request, String sessionKey) throws ApiException, ValidationException {

		checkInventoryGetRequest(request);
		
		if (StringUtils.isBlank(request.getFields())) {
			request.setFields("approve_status,num_iid,title,nick,type,cid,pic_url,num,props,valid_thru, list_time,price,has_discount,has_invoice,has_warranty,has_showcase, modified,delist_time,postage_id,seller_cids,outer_id");
		}

		ItemsInventoryGetResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total_results", response.getTotalResults());
		result.put("items", response.getItems());
		return result;
	}

	private static void checkInventoryGetRequest(ItemsInventoryGetRequest request) throws ValidationException{
		if (request.getPageNo() != null && request.getPageNo() <= 0) {
			throw new ValidationException("页码取值至少为1");
		}
		
		if (StringUtils.isNotBlank(request.getBanner()) && InventoryItemState.valueOf(request.getBanner()) == null){
			throw new ValidationException("传入商品状态不合法");
		}

		if (StringUtils.isNotBlank(request.getSellerCids())) {
			String[] split = StringUtils.split(request.getSellerCids(), ",");
			if (split.length > 32) {
				throw new ValidationException("卖家店铺内自定义类目ID最多为32个");
			}
		}

		if (StringUtils.isNotBlank(request.getOrderBy()) && !request.getOrderBy().matches("^\\w+:(asc|desc)$")) {
			throw new ValidationException("排序方式不合法");
		}

		if (request.getPageSize() != null && (request.getPageSize() < 1 || request.getPageSize() > 200)) {
			throw new ValidationException("每页显示条数范围为[1-200]");
		}
	}

	/**
	 * 下架商品状态
	 * 
	 * @author yang.hao
	 * @since 2011-8-8 下午04:16:11
	 *
	 */
	public enum InventoryItemState {
		/**
		 * (定时上架)
		 */
		regular_shelved,
		/**
		 * (从未上架)
		 */
		never_on_shelf,
		/**
		 * (全部卖完)
		 */
		sold_out,
		/**
		 * (我下架的)
		 */
		off_shelf,
		/**
		 * (等待所有上架)
		 */
		for_shelved,
		/**
		 * (违规下架的)
		 */
		violation_off_shelf
	}
}
