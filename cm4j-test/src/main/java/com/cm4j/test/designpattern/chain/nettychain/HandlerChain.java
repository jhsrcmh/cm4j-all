package com.cm4j.test.designpattern.chain.nettychain;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Netty中的责任链模式 - 动态链
 * HandlerChain - 持有首尾ctx，并维护一个map(map是用于存放键值对，不维护顺序),
 * 每个ctx内部维护其上一个和下一个ctx，每个ctx持有一个handler
 * 
 * HandlerChain [headCtx[prev,next,handler] , tailCtx[prev,next,handler] , map<String ,ctx>]
 * 
 * 优点：可以动态的向链条任意点添加handler，可自由调整handler的执行顺序
 * </pre>
 * 
 * @author yang.hao
 * @since 2011-5-3 上午11:21:04
 * 
 */
public class HandlerChain {
	private final Map<String, HandlerContext> name2ctx = new HashMap<String, HandlerContext>(4);
	// 因为可动态向链中添加首尾，故需要head和tail
	private volatile HandlerContext head;
	private volatile HandlerContext tail;

	public synchronized void addFirst(String name, Handler handler) {
		if (name2ctx.isEmpty()) {
			init(name, handler);
		} else {
			checkDuplicateName(name);
			HandlerContext oldHead = head;
			HandlerContext newHead = new HandlerContext(null, oldHead, handler);

			oldHead.prev = newHead;
			head = newHead;
			name2ctx.put(name, newHead);
		}
	}

	public synchronized void addLast(String name, Handler handler) {
		if (name2ctx.isEmpty()) {
			init(name, handler);
		} else {
			checkDuplicateName(name);
			HandlerContext oldTail = tail;
			HandlerContext newTail = new HandlerContext(oldTail, null, handler);

			oldTail.next = newTail;
			tail = newTail;
			name2ctx.put(name, newTail);
		}
	}

	public synchronized void addBefore(String baseName, String name, Handler handler) {
		HandlerContext baseCtx = name2ctx.get(baseName);
		if (baseCtx == head) {
			addFirst(name, handler);
		} else {
			checkDuplicateName(name);
			HandlerContext newCtx = new HandlerContext(baseCtx.prev, baseCtx, handler);

			baseCtx.prev.next = newCtx;
			baseCtx.prev = newCtx;

			name2ctx.put(name, newCtx);
		}
	}

	public synchronized void addAfter(String baseName, String name, Handler handler) {
		HandlerContext baseCtx = name2ctx.get(baseName);
		if (baseCtx == tail) {
			addLast(name, handler);
		} else {
			checkDuplicateName(name);
			HandlerContext newCtx = new HandlerContext(baseCtx, baseCtx.next, handler);

			baseCtx.next.prev = newCtx;
			baseCtx.next = newCtx;

			name2ctx.put(name, newCtx);
		}
	}

	private void init(String name, Handler handler) {
		HandlerContext ctx = new HandlerContext(null, null, handler);
		head = tail = ctx;
		name2ctx.clear();
		name2ctx.put(name, ctx);
	}

	private void checkDuplicateName(String name) {
		if (name2ctx.containsKey(name)) {
			throw new IllegalArgumentException("Duplicate handler name.");
		}
	}

	/**
	 * 正向循环链中所有的对象
	 */
	public void handleUpstream() {
		Handler h = null;
		try {
			if (head != null) {
				h = head.handler;
				h.handle();
			}

			HandlerContext ctx = head.next;
			while (ctx != null) {
				h = ctx.handler;
				h.handle();
				ctx = ctx.next;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * handler上下文，持有一个handler，并持有上一个和下一个 上下文ctx
	 * 
	 * @author yang.hao
	 * @since 2011-5-3 上午11:04:02
	 * 
	 */
	private class HandlerContext {
		// 维持上下文的前一个和下一个
		volatile HandlerContext prev;
		volatile HandlerContext next;

		private final Handler handler;

		public HandlerContext(HandlerContext prev, HandlerContext next, Handler handler) {
			this.prev = prev;
			this.next = next;
			this.handler = handler;
		}
	}
}
