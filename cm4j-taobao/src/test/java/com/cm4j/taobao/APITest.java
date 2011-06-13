package com.cm4j.taobao;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.UserGetRequest;
import com.taobao.api.response.UserGetResponse;

/**
 * <pre>
 * 沙箱环境：appkey:test appSecrect:test
 * 地址：http://mini.tbsandbox.com/
 * </pre>
 * 
 * @author Administrator
 * 
 */
public class APITest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	protected static String url = "http://gw.api.tbsandbox.com/router/rest";// 沙箱环境调用地址
	// 正式环境需要设置为:http://gw.api.taobao.com/router/rest
	protected static String appkey = "test";
	protected static String appSecret = "test";

	@Test
	public void testUserGet() {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, appSecret);// 实例化TopClient类
		UserGetRequest req = new UserGetRequest();// 实例化具体API对应的Request类
		req.setFields("nick,sex,buyer_credit,seller_credit ,created,last_visit");
		req.setNick("syniiii");
		UserGetResponse response;
		try {
			response = client.execute(req); // 执行API请求并打印结果
			logger.debug("result:" + response.getBody());
			logger.debug("nick:" + response.getUser().getNick());
		} catch (ApiException e) {
			// deal error
		}
	}

}
