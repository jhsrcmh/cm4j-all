package com.cm4j.taobao.service.async.quartz.jobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cm4j.taobao.api.common.APICaller;
import com.cm4j.taobao.service.async.quartz.QuartzJobData;
import com.cm4j.taobao.service.goods.items.ItemService;
import com.cm4j.taobao.utils.Converter;
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
		private String[] numIids_group1;
		/**
		 * 第二批商品ID
		 */
		private String[] numIids_group2;

		public String[] getNumIids_group1() {
			return numIids_group1;
		}

		public void setNumIids_group1(String[] numIids_group1) {
			this.numIids_group1 = numIids_group1;
		}

		public String[] getNumIids_group2() {
			return numIids_group2;
		}

		public void setNumIids_group2(String[] numIids_group2) {
			this.numIids_group2 = numIids_group2;
		}

	}

	@Override
	protected void handle(QuartzJobData data) throws Exception {
		if (data != null) {
			List<Item> items = ItemService.listShowcaseItems(true, data.getSessionKey());
			List<Long> deleteNumIids = new ArrayList<Long>();
			for (Item item : items) {
				deleteNumIids.add(item.getNumIid());
			}

			ItemService.batchRecommandDelete(deleteNumIids, data.getSessionKey());

			SeparateShowcaseData separateShowcaseData = APICaller.jsonBinder.fromJson(data.getJsonData(),
					SeparateShowcaseData.class);
			// todo 第二批？怎么办？
			ItemService.batchRecommandAdd(
					Converter.listConverter(Arrays.asList(separateShowcaseData.getNumIids_group1())),
					data.getSessionKey());
		}
	}

	@Override
	protected void handleException(Exception e) {
	}
}
