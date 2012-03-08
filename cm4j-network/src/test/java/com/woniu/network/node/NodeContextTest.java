package com.woniu.network.node;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.woniu.network.node.queue.BusinessHandler;

public class NodeContextTest {

	@Test
	public void test() throws InterruptedException {
		NodeContext ctx = new NodeContext(Executors.newFixedThreadPool(10), new BusinessHandler() {
			private AtomicInteger counter = new AtomicInteger();

			@Override
			public Response exec(Request request) {
				R r = (R) request;
				System.out.println(r);
				System.out.println(counter.incrementAndGet());
				return null;
			}
		});
		for (int i = 0; i < 1000000; i++) {
			ctx.put(new R(i));
		}
	}

	class R implements Request {
		private int i;

		public R(int i) {
			this.i = i;
		}
	}
}
