package com.cm4j.taobao.web.goods.items;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm4j.taobao.api.googds.items.ItemAPI;
import com.cm4j.taobao.exception.ValidationException;
import com.cm4j.taobao.service.goods.items.ItemService;
import com.cm4j.taobao.web.base.BaseDispatchAction;
import com.taobao.api.ApiException;

/**
 * 商品action
 * 
 * @author yang.hao
 * @since 2011-8-4 下午03:38:42
 * 
 */
@Controller
@RequestMapping("/safe")
public class ItemsAction extends BaseDispatchAction {
	
	/**
	 * 分页显示在销售的商品
	 * 
	 * @param page_size
	 * @param page_no
	 * @throws ApiException
	 * @throws ValidationException
	 */
	@RequestMapping("/list_onsale_items/{page_size}/{page_no}")
	public @ResponseBody
	Map<String, Object> list_onsale_items(@PathVariable Long page_size, @PathVariable Long page_no) throws ApiException,
			ValidationException {
		return ItemService.listOnsaleItems(page_no, page_size, super.getSessionKey());
	}

	/**
	 * 橱窗推荐
	 * 
	 * @param num_iid
	 *            商品ID
	 * @throws ValidationException
	 * @throws ApiException
	 */
	@RequestMapping("/recommend_add/${num_id}")
	public void recommend_add(@PathVariable Long num_iid) throws ApiException, ValidationException {
		ItemAPI.recommend_add(num_iid, getSessionKey());
	}

	/**
	 * 取消橱窗推荐
	 * 
	 * @param num_iid
	 *            商品ID
	 * @throws ValidationException
	 * @throws ApiException
	 */
	@RequestMapping("/recommend_delete/${num_id}")
	public void recommend_delete(@PathVariable Long num_iid) throws ApiException, ValidationException {
		ItemAPI.recommend_delete(num_iid, getSessionKey());
	}

}
