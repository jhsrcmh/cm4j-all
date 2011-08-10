package com.cm4j.taobao.api.user;

import java.util.List;

import javax.validation.ValidationException;

import org.apache.commons.lang.StringUtils;

import com.cm4j.taobao.api.common.APICaller;
import com.cm4j.taobao.api.common.DomainResolver;
import com.google.common.base.Joiner;
import com.taobao.api.ApiException;
import com.taobao.api.domain.User;
import com.taobao.api.request.UserGetRequest;
import com.taobao.api.response.UserGetResponse;

/**
 * 用户API包：taobao.user... taobao.sellercenter...等等
 * 
 * @author yang.hao
 * @since 2011-8-9 下午02:03:06
 * 
 */
public class UserAPI {

	/**
	 * taobao.user.get 获取单个用户信息
	 * 
	 * @param fields
	 * @param nick
	 *            <font
	 *            color=red>在传入session的情况下,可以不传nick，表示取当前用户信息；否则nick必须传</font>
	 * @param sessionKey
	 * @return
	 * @throws ApiException
	 */
	public static User user_get(String fields, String nick, String sessionKey) throws ApiException {
		UserGetRequest request = new UserGetRequest();
		request.setNick(nick);

		if (StringUtils.isBlank(fields)) {
			List<String> values = DomainResolver.getApiFieldValues(User.class);
			request.setFields(Joiner.on(",").join(values));
		} else {
			request.setFields(fields);
		}

		if (StringUtils.isBlank(nick) && StringUtils.isBlank(sessionKey)) {
			throw new ValidationException("参数SessionKey和nick不能都为空");
		}

		UserGetResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);

		return response.getUser();
	}
}
