package com.cm4j.taobao.service.goods.items;

import java.util.Map;

import com.cm4j.taobao.api.googds.items.ItemsAPI;
import com.cm4j.taobao.exception.ValidationException;
import com.taobao.api.ApiException;
import com.taobao.api.request.ItemsOnsaleGetRequest;

/**
 * 商品类服务
 * 
 * @author yang.hao
 * @since 2011-8-4 下午02:45:33
 * 
 */
public class ItemService {

	/**
	 * 分页获取在销售的商品列表
	 * 
	 * @param sessionKey
	 * 
	 * @param pageNo
	 *            获取页码
	 * @param pageSize
	 *            每页大小
	 * @param sessionKey
	 * @return total_results - 结果总数<br />
	 *         items - [ID,标题,商品上传后的状态,下架时间,是否橱窗推荐]
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public static Map<String, Object> listOnsaleItems(Long pageNo, Long pageSize, String sessionKey)
			throws ApiException, ValidationException {
		if (pageNo == null) {
			throw new ValidationException("参数页码不允许为空");
		}
		if (pageSize == null) {
			throw new ValidationException("参数每页显示量不允许为空");
		}

		ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);
		request.setFields("num_iid,title,approve_status,delist_time,has_showcase");
		return ItemsAPI.onsale_get(request, sessionKey);
	}
}
