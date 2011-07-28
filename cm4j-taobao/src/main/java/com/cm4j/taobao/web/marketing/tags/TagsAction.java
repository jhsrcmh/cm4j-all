package com.cm4j.taobao.web.marketing.tags;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm4j.core.utils.JsonBinder;
import com.cm4j.taobao.api.marketing.tags.TagsService;
import com.cm4j.taobao.web.base.BaseDispatchAction;
import com.taobao.api.ApiException;
import com.taobao.api.domain.UserTag;

@Controller
@RequestMapping("/safe/marketing/tags")
public class TagsAction extends BaseDispatchAction {

	@Autowired
	private TagsService tagsService;

	/**
	 * 查询人群标签
	 * 
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping("/get")
	public @ResponseBody
	String get() throws ApiException {
		List<UserTag> list = tagsService.get(super.getSessionKey());
		if (list == null) {
			return "{}";
		} else {
			return JsonBinder.NON_DEFAULT.toJson(list);
		}
	}
}
