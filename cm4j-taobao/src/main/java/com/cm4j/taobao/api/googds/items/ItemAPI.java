package com.cm4j.taobao.api.googds.items;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cm4j.taobao.api.common.APICaller;
import com.cm4j.taobao.api.common.DomainResolver;
import com.cm4j.taobao.exception.ValidationException;
import com.google.common.base.Joiner;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.request.ItemRecommendAddRequest;
import com.taobao.api.request.ItemRecommendDeleteRequest;
import com.taobao.api.request.ItemUpdateDelistingRequest;
import com.taobao.api.request.ItemUpdateListingRequest;
import com.taobao.api.response.ItemGetResponse;
import com.taobao.api.response.ItemRecommendAddResponse;
import com.taobao.api.response.ItemRecommendDeleteResponse;
import com.taobao.api.response.ItemUpdateDelistingResponse;
import com.taobao.api.response.ItemUpdateListingResponse;

/**
 * 商品API包：taobao.item...
 * 
 * @author yang.hao
 * @since 2011-7-29 上午10:10:22
 * 
 */
public class ItemAPI {

	/**
	 * taobao.item.get 得到单个商品信息
	 * 
	 * @param num_iid
	 * @param fields
	 * @param sessionKey
	 * @return
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public static Item get(Long num_iid, String fields, String sessionKey) throws ApiException, ValidationException {
		ItemGetRequest request = new ItemGetRequest();
		if (num_iid == null) {
			throw new ValidationException("商品数字ID不允许为空");
		}
		request.setNumIid(num_iid);
		if (StringUtils.isBlank(fields)) {
			List<String> values = DomainResolver.getApiFieldValues(Item.class);
			request.setFields(Joiner.on(",").join(values));
		} else {
			request.setFields(fields);
		}

		ItemGetResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);

		return response.getItem();
	}

	/**
	 * taobao.item.recommend.add 橱窗推荐一个商品
	 * 
	 * @param num_iid
	 * @param sessionKey
	 * @return
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public static Item recommend_add(Long num_iid, String sessionKey) throws ApiException, ValidationException {
		if (num_iid == null) {
			throw new ValidationException("商品ID不允许为空");
		}
		ItemRecommendAddRequest request = new ItemRecommendAddRequest();
		request.setNumIid(num_iid);
		ItemRecommendAddResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);

		return response.getItem();
	}

	/**
	 * taobao.item.recommend.delete 取消橱窗推荐一个商品
	 * 
	 * @param num_iid
	 * @param sessionKey
	 * @return
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public static Item recommend_delete(Long num_iid, String sessionKey) throws ApiException, ValidationException {
		if (num_iid == null) {
			throw new ValidationException("商品ID不允许为空");
		}
		ItemRecommendDeleteRequest request = new ItemRecommendDeleteRequest();
		request.setNumIid(num_iid);
		ItemRecommendDeleteResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);

		return response.getItem();
	}

	/**
	 * taobao.item.update.listing 一口价商品上架
	 * 
	 * @param num_iid
	 * @param sessionKey
	 * @return
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public static Item update_listing(Long num_iid, String sessionKey) throws ApiException, ValidationException {
		if (num_iid == null) {
			throw new ValidationException("商品ID不允许为空");
		}
		ItemUpdateListingRequest request = new ItemUpdateListingRequest();
		request.setNumIid(num_iid);
		ItemUpdateListingResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);

		return response.getItem();
	}

	/**
	 * taobao.item.update.delisting 商品下架
	 * 
	 * @param num_iid
	 * @param sessionKey
	 * @return
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public static Item update_delisting(Long num_iid, String sessionKey) throws ApiException, ValidationException {
		if (num_iid == null) {
			throw new ValidationException("商品ID不允许为空");
		}
		ItemUpdateDelistingRequest request = new ItemUpdateDelistingRequest();
		request.setNumIid(num_iid);
		ItemUpdateDelistingResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);

		return response.getItem();
	}
}
