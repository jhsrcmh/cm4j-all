package com.woniu.network.node.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woniu.network.node.Request;
import com.woniu.network.node.Response;

public class ServerBusinessHandler implements BusinessHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Response exec(Request request) {
		logger.debug("received message:{}", request);
		// todo
		return null;
	}

}
