package com.cm4j.taobao.api.googds.items;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cm4j.taobao.api.common.APICaller;
import com.cm4j.taobao.exception.ValidationException;
import com.taobao.api.ApiException;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.response.ItemsOnsaleGetResponse;

/**
 * 商品API包：taobao.items...
 * 
 * @author yang.hao
 * @since 2011-7-27 上午10:57:27
 * 
 */
public class ItemsAPI {

	/**
	 * taobao.items.onsale.get 获取当前会话用户出售中的商品列表
	 * 
	 * @param request
	 *            fields 值是固定的，具体查看文档
	 * @param sessionKey
	 * @return total_results - 必须，结果总数<br />
	 *         items - 必须，搜索到的商品列表，具体字段根据设定的fields决定，不包括desc字段
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public static Map<String, Object> onsale_get(ItemsOnsaleGetRequest request, String sessionKey) throws ApiException,
			ValidationException {
		checkOnsaleGetRequest(request);

		if (StringUtils.isBlank(request.getFields())) {
			request.setFields("approve_status,num_iid,title,nick,type,cid,pic_url,num,props,valid_thru,list_time,price,has_discount,has_invoice,has_warranty,has_showcase,modified,delist_time,postage_id,seller_cids,outer_id");
		}

		ItemsOnsaleGetResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total_results", response.getTotalResults());
		result.put("items", response.getItems());
		return result;
	}

	private static void checkOnsaleGetRequest(ItemsOnsaleGetRequest request) throws ValidationException {
		if (request.getPageNo() != null && request.getPageNo() <= 0) {
			throw new ValidationException("页码取值至少为1");
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
}
