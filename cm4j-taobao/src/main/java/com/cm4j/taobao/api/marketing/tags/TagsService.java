package com.cm4j.taobao.api.marketing.tags;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.cm4j.taobao.api.common.APICaller;
import com.taobao.api.ApiException;
import com.taobao.api.domain.UserTag;
import com.taobao.api.request.MarketingTagsGetRequest;
import com.taobao.api.response.MarketingTagsGetResponse;

/**
 * API包：taobao.marketing.tags...
 * 
 * @author yang.hao
 * @since 2011-7-27 上午11:02:45
 * 
 */
@Service
public class TagsService {

	/**
	 * taobao.marketing.tags.get 查询人群标签
	 * 
	 * @param sessionKey
	 * @return
	 * @throws ApiException
	 */
	public List<UserTag> get(String sessionKey) throws ApiException {
		MarketingTagsGetRequest request = new MarketingTagsGetRequest();

		if (StringUtils.isBlank(request.getFields())) {
			request.setFields("tag_id,tag_name,create_date,description");
		}

		MarketingTagsGetResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);

		List<UserTag> userTags = response.getUserTags();
		return userTags;
	}
}
