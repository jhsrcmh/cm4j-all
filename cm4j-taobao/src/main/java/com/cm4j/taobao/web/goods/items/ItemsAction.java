package com.cm4j.taobao.web.goods.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm4j.taobao.exception.ValidationException;
import com.cm4j.taobao.service.goods.items.ItemService;
import com.cm4j.taobao.web.base.BaseDispatchAction;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;

/**
 * 商品action
 * 
 * @author yang.hao
 * @since 2011-8-4 下午03:38:42
 * 
 */
@Controller
@RequestMapping("/secure/items")
public class ItemsAction extends BaseDispatchAction {
	
	/**
	 * 分页显示在销售的商品
	 * 
	 * @param page_size
	 * @param page_no
	 * @throws ApiException
	 * @throws ValidationException
	 */
	@RequestMapping("/list_onsale")
	public @ResponseBody
	Map<String, Object> list_onsale_items( Long page_size, Long page_no)
			throws ApiException, ValidationException {
		return ItemService.listOnsaleItems(page_size, page_no, null, getSessionKey());
	}

	
	/**
	 * 跳转至分批橱窗推荐
	 * 
	 * @return
	 */
	@RequestMapping("/separate_showcase/prepare")
	public String separate_showcase_prepare (){
		return "/showcase/separate_showcase";
	}
	
	/**
	 * 分批橱窗推荐
	 * 
	 * @return
	 */
	@RequestMapping("/separate_showcase")
	public String separate_showcase (){
		return null;
	}
	
	/**
	 * 分页显示橱窗推荐的商品
	 * 
	 * @param has_showcase
	 *            是否橱窗推荐
	 * @param page_size
	 * @param page_no
	 * @return
	 * @throws ApiException
	 * @throws ValidationException
	 */
	@RequestMapping("/list_showcase/{has_showcase}/{page_size}/{page_no}")
	public @ResponseBody
	Map<String, Object> list_showcase_items(@PathVariable Boolean has_showcase, @PathVariable Long page_size,
			@PathVariable Long page_no) throws ApiException, ValidationException {
		return ItemService.listShowcaseItems(page_size, page_no, has_showcase, getSessionKey());
	}

	/**
	 * 橱窗推荐和取消
	 * 
	 * @param operation
	 *            add - 橱窗推荐<br />
	 *            delete - 取消橱窗推荐
	 * @param num_iids
	 *            商品ID，多个以,分隔
	 * @return
	 * @throws ValidationException
	 * @throws ApiException
	 */
	@RequestMapping("/recommend/{operation}/{num_iids}")
	public @ResponseBody
	List<Item> recommend(@PathVariable String operation, @PathVariable List<String> num_iids) throws ApiException,
			ValidationException {
		if ("add".equals(operation)) {
			return ItemService.batchRecommandAdd(listConverter(num_iids), getSessionKey());
		} else if (("delete").equals(operation)) {
			return ItemService.batchRecommandDelete(listConverter(num_iids), getSessionKey());
		}
		return null;
	}

	/**
	 * 一口价商品上架
	 * 
	 * @param operation
	 *            listing - 一口价商品上架<br />
	 *            delisting - 商品下架
	 * @param num_iids
	 *            商品ID，多个以,分隔
	 * @return
	 * @throws ApiException
	 * @throws ValidationException
	 */
	@RequestMapping("/update/{operation}/{num_iids}")
	public @ResponseBody
	List<Item> update_listing(@PathVariable String operation, @PathVariable List<String> num_iids) throws ApiException,
			ValidationException {
		if ("listing".equals(operation)) {
			ItemService.batchUpdateListing(listConverter(num_iids), getSessionKey());
		} else if ("delisting".equals(operation)) {
			ItemService.batchUpdateDelisting(listConverter(num_iids), getSessionKey());
		}
		return null;
	}

	/**
	 * 将List的String类型转换为List的Long类型
	 * 
	 * @param list
	 * @return
	 */
	private List<Long> listConverter(List<String> list) {
		List<Long> result = new ArrayList<Long>();
		for (String element : list) {
			if (StringUtils.isNotBlank(element)){
				result.add(NumberUtils.toLong(element));
			}
		}
		return result;
	}
}
