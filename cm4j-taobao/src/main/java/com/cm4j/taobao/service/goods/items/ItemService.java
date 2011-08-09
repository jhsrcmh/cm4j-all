package com.cm4j.taobao.service.goods.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cm4j.taobao.api.googds.items.ItemAPI;
import com.cm4j.taobao.api.googds.items.ItemsAPI;
import com.cm4j.taobao.exception.ValidationException;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
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
	 * @param pageSize
	 *            每页大小
	 * @param pageNo
	 *            获取页码
	 * @param orderBy
	 *            排序方式。格式为column:asc/desc，column可选值:list_time(上架时间),delist_time(
	 *            下架时间),num(商品数量)，modified(最近修改时间);默认上架时间降序(即最新上架排在前面)。
	 *            如按照上架时间降序排序方式为list_time:desc
	 * @param sessionKey
	 * @return total_results - 结果总数<br />
	 *         items - [ID,标题,商品上传后的状态,下架时间,是否橱窗推荐]
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public static Map<String, Object> listOnsaleItems(Long pageSize, Long pageNo, String orderBy, String sessionKey)
			throws ApiException, ValidationException {
		return listOnsaleItems(pageSize, pageNo, null, null, orderBy, sessionKey);
	}

	/**
	 * 分页获取橱窗推荐商品列表
	 * 
	 * @param pageSize
	 *            每页大小
	 * @param pageNo
	 *            获取页码
	 * @param hasShowcase
	 *            是否橱窗推荐
	 * @param sessionKey
	 * @return
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public static Map<String, Object> listShowcaseItems(Long pageSize, Long pageNo, boolean hasShowcase,
			String sessionKey) throws ApiException, ValidationException {
		return listOnsaleItems(pageSize, pageNo, null, hasShowcase, null, sessionKey);
	}

	/**
	 * 批量橱窗推荐
	 * 
	 * @param num_iids
	 * @param sessionKey
	 * @return
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public static List<Item> batchRecommandAdd(List<Long> num_iids, String sessionKey) throws ApiException,
			ValidationException {
		List<Item> items = new ArrayList<Item>();
		for (Long num_iid : num_iids) {
			items.add(ItemAPI.recommend_add(num_iid, sessionKey));
		}
		return items;
	}

	/**
	 * 批量取消橱窗推荐
	 * 
	 * @param num_iids
	 * @param sessionKey
	 * @return
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public static List<Item> batchRecommandDelete(List<Long> num_iids, String sessionKey) throws ApiException,
			ValidationException {
		List<Item> items = new ArrayList<Item>();
		for (Long num_iid : num_iids) {
			items.add(ItemAPI.recommend_delete(num_iid, sessionKey));
		}
		return items;
	}

	/**
	 * 一口价批量上架
	 * 
	 * @param num_iids
	 * @param sessionKey
	 * @return
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public static List<Item> batchUpdateListing(List<Long> num_iids, String sessionKey) throws ApiException,
			ValidationException {
		List<Item> items = new ArrayList<Item>();
		for (Long num_iid : num_iids) {
			items.add(ItemAPI.update_listing(num_iid, sessionKey));
		}
		return items;
	}

	/**
	 * 批量下架
	 * 
	 * @param num_iids
	 * @param sessionKey
	 * @return
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public static List<Item> batchUpdateDelisting(List<Long> num_iids, String sessionKey) throws ApiException,
			ValidationException {
		List<Item> items = new ArrayList<Item>();
		for (Long num_iid : num_iids) {
			items.add(ItemAPI.update_delisting(num_iid, sessionKey));
		}
		return items;
	}

	/**
	 * 分页获取在销售的商品列表
	 * 
	 * @param sessionKey
	 * 
	 * @param pageSize
	 *            每页大小
	 * @param pageNo
	 *            获取页码
	 * @param hasDiscount
	 *            是否参与折扣
	 * @param hasShowcase
	 *            是否橱窗推荐
	 * @param orderBy
	 *            排序方式。格式为column:asc/desc，column可选值:list_time(上架时间),delist_time(
	 *            下架时间),num(商品数量)，modified(最近修改时间);默认上架时间降序(即最新上架排在前面)。
	 *            如按照上架时间降序排序方式为list_time:desc
	 * @param sessionKey
	 * @return total_results - 结果总数<br />
	 *         items - [ID,标题,商品上传后的状态,下架时间,是否橱窗推荐]
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public static Map<String, Object> listOnsaleItems(Long pageSize, Long pageNo, Boolean hasDiscount,
			Boolean hasShowcase, String orderBy, String sessionKey) throws ApiException, ValidationException {
		if (pageNo == null) {
			throw new ValidationException("参数页码不允许为空");
		}
		if (pageSize == null) {
			throw new ValidationException("参数每页显示量不允许为空");
		}

		ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);
		request.setOrderBy(orderBy);
		request.setHasShowcase(hasShowcase);
		request.setHasDiscount(hasDiscount);
		request.setFields("num_iid,title,price,approve_status,delist_time,has_showcase,pic_url");
		return ItemsAPI.onsale_get(request, sessionKey);
	}

}
