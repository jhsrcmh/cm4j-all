package com.cm4j.test.tcp_ip.node_chain;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

/**
 * 服务器端节点处理器
 * 
 * @author yang.hao
 * @since 2011-12-1 下午4:00:01
 */
public class ServerNodeHandler implements NodeHandler {

	private CompletionService<?> completionService = new ExecutorCompletionService(Executors.newFixedThreadPool(Runtime
			.getRuntime().availableProcessors() * 2));

	public void execute() {
		completionService.submit(null);
	}
}
