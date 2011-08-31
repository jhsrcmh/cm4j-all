package com.cm4j.taobao.service.async.quartz.jobs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.cm4j.taobao.api.common.APICaller;
import com.cm4j.taobao.dao.AsyncTaskLogDao;
import com.cm4j.taobao.service.async.quartz.data.QuartzJobData;
import com.cm4j.taobao.service.goods.items.ItemService;
import com.google.common.base.Joiner;
import com.taobao.api.domain.Item;

/**
 * 定时任务-分批橱窗推荐
 * 
 * @author yang.hao
 * 
 */
public class SeparateShowcase extends AbstractJobHandler {

	/**
	 * 存放于表中的数据
	 * 
	 * @author yang.hao
	 * 
	 */
	public static class SeparateShowcaseData {
		/**
		 * 第一批商品ID
		 */
		private List<Long> numIids_group1;
		/**
		 * 第二批商品ID
		 */
		private List<Long> numIids_group2;

		public List<Long> getNumIids_group1() {
			return numIids_group1;
		}

		public void setNumIids_group1(List<Long> numIids_group1) {
			this.numIids_group1 = numIids_group1;
		}

		public List<Long> getNumIids_group2() {
			return numIids_group2;
		}

		public void setNumIids_group2(List<Long> numIids_group2) {
			this.numIids_group2 = numIids_group2;
		}
	}

	@Override
	protected String handle(QuartzJobData data, ApplicationContext ctx) throws Exception {
		String result = null;
		if (data != null) {
			// 查询正在橱窗推荐的商品
			List<Item> items = ItemService.listShowcaseItems(true, data.getSessionKey());
			List<Long> deleteNumIids = new ArrayList<Long>();
			for (Item item : items) {
				deleteNumIids.add(item.getNumIid());
			}

			// 批量取消橱窗推荐
			ItemService.batchRecommandDelete(deleteNumIids, data.getSessionKey());

			// 查询本次任务是第几次执行
			AsyncTaskLogDao asyncTaskLogDao = ctx.getBean(AsyncTaskLogDao.class);
			int execCount = asyncTaskLogDao.countByTaskId(data.getTaskId());
			// 执行来源
			SeparateShowcaseData separateShowcaseData = APICaller.jsonBinder.fromJson(data.getJsonData(),
					SeparateShowcaseData.class);
			// 分批执行
			if (execCount % 2 == 0) {
				ItemService.batchRecommandAdd(separateShowcaseData.getNumIids_group1(), data.getSessionKey());
				result = Joiner.on(",").join(separateShowcaseData.getNumIids_group1());
			} else {
				ItemService.batchRecommandAdd(separateShowcaseData.getNumIids_group2(), data.getSessionKey());
				result = Joiner.on(",").join(separateShowcaseData.getNumIids_group2());
			}
		}
		return result;
	}

	@Override
	protected void handleException(Exception e) {
	}
}
