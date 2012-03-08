package com.woniu.network.node;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.woniu.network.exception.NodeExectionException;

public class Node {

	private Future<Response> future;
	private static final long TIME_OUT = 3000;
	private long timeout; // milliseconds

	public Node(ExecutorService executor, NodeCallable task) {
		this(executor, task, TIME_OUT);
	}

	public Node(ExecutorService executor, NodeCallable task, long timeout) {
		this.timeout = timeout;
		this.future = executor.submit(task);
	}

	public Response get() throws TimeoutException {
		if (future == null) {
			throw new NodeExectionException("future is null,task not submit to thread pool!");
		}
		try {
			return future.get(timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new NodeExectionException("node thread execute error", e);
		} catch (ExecutionException e) {
			throw new NodeExectionException("node thread execute error", e);
		}
	}
}
