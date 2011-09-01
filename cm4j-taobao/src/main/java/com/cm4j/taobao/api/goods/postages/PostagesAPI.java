package com.cm4j.taobao.api.goods.postages;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.cm4j.taobao.api.common.APICaller;
import com.cm4j.taobao.api.common.DomainResolver;
import com.google.common.base.Joiner;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Postage;
import com.taobao.api.request.PostagesGetRequest;
import com.taobao.api.response.PostagesGetResponse;

/**
 * API包：taobao.postages...
 * 
 * @author yang.hao
 * @since 2011-7-30 下午02:08:30
 * 
 */
@Service
public class PostagesAPI {

	/**
	 * taobao.postages.get 获取卖家的运费模板
	 * 
	 * @param fields
	 *            需返回的字段列表.可选值:Postage结构体中的所有字段;字段之间用","分隔.
	 * @return
	 * @throws ApiException
	 */
	public List<Postage> get(String fields, String sessionKey) throws ApiException {
		PostagesGetRequest request = new PostagesGetRequest();

		if (StringUtils.isBlank(fields)) {
			List<String> values = DomainResolver.getApiFieldValues(Postage.class);
			request.setFields(Joiner.on(",").join(values));
		} else {
			request.setFields(fields);
		}

		PostagesGetResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);

		return response.getPostages();
	}
}
