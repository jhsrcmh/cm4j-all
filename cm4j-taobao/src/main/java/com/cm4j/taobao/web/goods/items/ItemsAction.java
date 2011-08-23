package com.cm4j.taobao.web.goods.items;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.cm4j.taobao.api.common.APICaller;
import com.cm4j.taobao.api.shop.ShopAPI;
import com.cm4j.taobao.dao.AsyncTaskDao;
import com.cm4j.taobao.exception.ValidationException;
import com.cm4j.taobao.pojo.AsyncTask;
import com.cm4j.taobao.pojo.AsyncTask.State;
import com.cm4j.taobao.pojo.AsyncTask.TaskSubType;
import com.cm4j.taobao.pojo.AsyncTask.TaskType;
import com.cm4j.taobao.service.async.quartz.jobs.SeparateShowcase.SeparateShowcaseData;
import com.cm4j.taobao.service.goods.items.ItemService;
import com.cm4j.taobao.utils.Converter;
import com.cm4j.taobao.web.base.BaseDispatchAction;
import com.cm4j.taobao.web.login.UserSession;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Shop;

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

	@Autowired
	private AsyncTaskDao asyncTaskDao;

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
	Map<String, Object> list_onsale_items(Long page_size, Long page_no) throws ApiException, ValidationException {
		return ItemService.listOnsaleItems(page_size, page_no, null, getSessionKey());
	}

	/**
	 * 跳转至分批橱窗推荐
	 * 
	 * @return
	 */
	@RequestMapping("/separate_showcase/prepare")
	public String separate_showcase_prepare() {
		return "/showcase/separate_showcase";
	}

	/**
	 * 分批橱窗推荐
	 * 
	 * @param numIids_group1
	 * @param numIids_group2
	 * @param interval
	 * @return
	 * @throws ApiException
	 * @throws ValidationException
	 */
	@RequestMapping("/separate_showcase")
	public ModelAndView separate_showcase(String numIids_group1, String numIids_group2, int interval)
			throws ApiException, ValidationException {
		String[] str_group1 = StringUtils.split(numIids_group1, ",");
		String[] str_group2 = StringUtils.split(numIids_group2, ",");
		String[] obj_group1 = (String[]) ArrayUtils.removeElement(str_group1, "");
		String[] obj_group2 = (String[]) ArrayUtils.removeElement(str_group2, "");

		Shop shop = ShopAPI.remainshowcase_get(getSessionKey());
		if (shop == null) {
			throw new ValidationException("查询商品剩余橱窗数量异常，请重试！");
		}
		Long allCount = shop.getAllCount();

		if (ArrayUtils.isEmpty(obj_group1) || obj_group1.length > allCount) {
			throw new ValidationException("第一批橱窗推荐商品数量不在范围之内：[1," + allCount + "]！");
		}
		if (ArrayUtils.isEmpty(obj_group2) || obj_group2.length > allCount) {
			throw new ValidationException("第二批橱窗推荐商品数量不在范围之内：[1," + allCount + "]！");
		}

		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(getRequest(), UserSession.SESSION_NAME);
		Long userId = userSession.getVisitor_id();
		List<AsyncTask> list = asyncTaskDao.findAllByProperty(new String[] { "relatedId", "taskType", "taskSubType" },
				new Object[] { userId, TaskType.cron.name(), TaskSubType.separate_showcase.name() });
		if (list.size() > 0) {
			throw new ValidationException("对不起，您已设置了分批橱窗推荐，请禁用原设置后再添加！");
		}

		// 插入异步cron任务
		AsyncTask asyncTask = new AsyncTask();
		asyncTask.setTaskType(TaskType.cron.name());
		asyncTask.setTaskSubType(TaskSubType.separate_showcase.name());

		asyncTask.setRelatedId(userId);
		asyncTask.setTaskCron("0 0 0/" + interval + " * * ?");
		asyncTask.setStartDate(AsyncTask.DATE_NOW.apply());
		asyncTask.setEndDate(AsyncTask.DATE_FOREVER.apply());
		asyncTask.setState(State.wating_operate.name());

		SeparateShowcaseData data = new SeparateShowcaseData();
		data.setNumIids_group1(obj_group1);
		data.setNumIids_group2(obj_group2);
		asyncTask.setTaskData(APICaller.jsonBinder.toJson(data));

		asyncTaskDao.save(asyncTask);

		return new ModelAndView(SUCCESS_PAGE, Collections.singletonMap(MESSAGE_KEY, "恭喜您，商品分批橱窗推荐设置成功！"));
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
			return ItemService.batchRecommandAdd(Converter.listConverter(num_iids), getSessionKey());
		} else if (("delete").equals(operation)) {
			return ItemService.batchRecommandDelete(Converter.listConverter(num_iids), getSessionKey());
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
			return ItemService.batchUpdateListing(Converter.listConverter(num_iids), getSessionKey());
		} else if ("delisting".equals(operation)) {
			return ItemService.batchUpdateDelisting(Converter.listConverter(num_iids), getSessionKey());
		}
		return null;
	}

}
