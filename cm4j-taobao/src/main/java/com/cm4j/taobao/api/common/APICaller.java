package com.cm4j.taobao.api.common;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.TaobaoRequest;
import com.taobao.api.TaobaoResponse;
import com.taobao.api.request.TimeGetRequest;
import com.taobao.api.response.TimeGetResponse;

/**
 * 通用API调用服务
 * 
 * @author yang.hao
 * @since 2011-7-26 下午04:05:02
 * 
 */
public class APICaller {

	public static final Logger logger = LoggerFactory.getLogger(APICaller.class);

	/**
	 * 调用API服务
	 * 
	 * @param <T>
	 * @param request
	 * @param sessionKey
	 *            为空则认为不需要sessionKey
	 * @return
	 * @throws ApiException
	 */
	public static <T extends TaobaoResponse> T call(TaobaoRequest<T> request, String sessionKey) throws ApiException {
		TaobaoClient client = new DefaultTaobaoClient(APIConstants.getTaobaoServiceUrl(), APIConstants.getAppKey(),
				APIConstants.getAppSecret());
		if (StringUtils.isNotBlank(sessionKey)) {
			return client.execute(request, sessionKey);
		} else {
			return client.execute(request);
		}
	}

	/**
	 * 获取淘宝API当前时间
	 * 
	 * @return
	 * @throws ApiException
	 */
	public static Date getTaobaoTime() {
		Date date = new Date();
		try {
			TimeGetResponse response = call(new TimeGetRequest(), null);
			date = response.getTime();
		} catch (Exception e) {
			logger.error("API异常:获取当前时间异常", e);
		}
		return date;
	}

	/**
	 * 解析TaobaoResponse，如果返回不正常则抛ApiException
	 * 
	 * @param response
	 * @throws ApiException
	 */
	public static void resolveResponseException(TaobaoResponse response) throws ApiException {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(response.getErrorCode())) {
			sb.append("errorCode:" + response.getErrorCode());
		}
		if (StringUtils.isNotBlank(response.getMsg())) {
			sb.append(",msg:" + response.getMsg());
		}
		if (StringUtils.isNotBlank(response.getSubCode())) {
			sb.append(",subCode:" + response.getSubCode());
		}
		if (StringUtils.isNotBlank(response.getSubMsg())) {
			sb.append(",subMsg:" + response.getSubMsg());
		}
		if (StringUtils.isNotBlank(sb.toString())) {
			throw new ApiException(sb.toString());
		}
	}
}
