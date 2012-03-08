package com.woniu.network.node;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import com.woniu.network.node.queue.BusinessHandler;

public class NodeContext {

	private BlockingQueue<Node> queue;
	private ExecutorService executor;
	private BusinessHandler handler;

	public NodeContext(ExecutorService executor, BusinessHandler handler ) {
		this.queue = new LinkedBlockingQueue<Node>(200);
		this.executor = executor;
		this.handler = handler;
//		new Thread(queueConsumer).start();
	}

	public void put(Request request) throws InterruptedException {
		Node node = new Node(executor, new NodeCallable(handler, request));
		queue.put(node);
	}
}
