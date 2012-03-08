package com.woniu.network.node;

import java.util.concurrent.Callable;

import com.woniu.network.node.queue.BusinessHandler;

public class NodeCallable implements Callable<Response> {

	private BusinessHandler handler;
	private Request request;

	public NodeCallable(BusinessHandler handler, Request request) {
		this.handler = handler;
		this.request = request;
	}

	@Override
	public Response call() throws Exception {
		return handler.exec(request);
	}

}
