package com.woniu.network.node.queue;

import com.woniu.network.node.Request;
import com.woniu.network.node.Response;

/**
 * 业务处理
 * 
 * @author yang.hao
 * @since 2011-12-13 上午9:57:14
 */
public interface BusinessHandler {

	public Response exec(Request request);
}
