package com.cm4j.taobao.api.googds.items;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.cm4j.taobao.api.common.APICaller;
import com.cm4j.taobao.api.common.DomainResolver;
import com.cm4j.taobao.exception.ValidationException;
import com.google.common.base.Joiner;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.response.ItemGetResponse;

/**
 * API包：taobao.item...
 * 
 * @author yang.hao
 * @since 2011-7-29 上午10:10:22
 * 
 */
@Service
public class ItemService {

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
	public Item get(Long num_iid, String fields, String sessionKey) throws ApiException, ValidationException {
		ItemGetRequest request = new ItemGetRequest();
		if (num_iid == null) {
			throw new ValidationException("商品数字ID不允许为空");
		}
		request.setNumIid(num_iid);
		if (StringUtils.isBlank(fields)) {
			List<String> values = DomainResolver.getApiFieldValues(Item.class);
			request.setFields(Joiner.on(",").join(values));
		}
		
		ItemGetResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);
		
		return response.getItem();
	}
}
