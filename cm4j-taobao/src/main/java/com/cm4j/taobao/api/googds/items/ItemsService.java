package com.cm4j.taobao.api.googds.items;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.cm4j.taobao.api.common.APICaller;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.response.ItemsOnsaleGetResponse;

/**
 * API包：taobao.items...
 * 
 * @author yang.hao
 * @since 2011-7-27 上午10:57:27
 * 
 */
@Service
public class ItemsService {

	/**
	 * taobao.items.onsale.get 获取当前会话用户出售中的商品列表
	 * 
	 * @param request
	 * @param sessionKey
	 * @return [结果总数，结果]
	 * @throws ApiException
	 */
	public Object[] onsale_get(ItemsOnsaleGetRequest request, String sessionKey) throws ApiException {
		if (StringUtils.isBlank(request.getFields())) {
			request.setFields("approve_status,num_iid,title,nick,type,cid,pic_url,num,props,valid_thru,list_time,price,has_discount,has_invoice,has_warranty,has_showcase,modified,delist_time,postage_id,seller_cids,outer_id");
		}

		ItemsOnsaleGetResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);
		
		List<Item> items = response.getItems();
		Long totalResults = response.getTotalResults();

		return new Object[] { totalResults, items };
	}
}
