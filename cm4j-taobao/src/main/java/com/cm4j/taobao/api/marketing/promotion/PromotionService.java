package com.cm4j.taobao.api.marketing.promotion;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cm4j.taobao.api.common.APICaller;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Promotion;
import com.taobao.api.request.MarketingPromotionAddRequest;
import com.taobao.api.response.MarketingPromotionAddResponse;

/**
 * API包:taobao.marketing.promotion...
 * 
 * @author yang.hao
 * @since 2011-7-27 下午01:05:45
 * 
 */
@Service
public class PromotionService {

	/**
	 * taobao.marketing.promotion.add request 设置商品定向优惠策略
	 * 
	 * @param request
	 * @param sessionKey
	 * @return 
	 *         [是否成功，设置成功的优惠信息(仅返回Promotion数据结构中的promotion_id,item_id,item_detail_url
	 *         )]
	 * @throws ApiException
	 */
	public Object[] add(MarketingPromotionAddRequest request, String sessionKey) throws ApiException {
		MarketingPromotionAddResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);

		Boolean isSuccess = response.getIsSuccess();
		List<Promotion> promotions = response.getPromotions();
		return new Object[] { isSuccess, promotions };
	}
}
