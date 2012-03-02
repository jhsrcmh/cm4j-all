package com.cm4j.test.tcp_ip.node_chain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 动态链上下文<br>
 * 自动增加大小<br>
 * 自动删除已读写节点;
 * 
 * @since 2011-11-24 下午5:44:46
 */
public class DynamicNodePipeline {

	private Node head;
	private Node tail;

	private final Map<Long, Node> id2node = new ConcurrentHashMap<Long, DynamicNodePipeline.Node>(4);

	private long handleIndex;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public DynamicNodePipeline() {
		Thread t = new Thread(new NodeRecycler(), "node-recycler");
		t.setDaemon(true);
		t.start();
	}

	public synchronized long addLast(NodeHandler handler) {
		// StopWatch watch = new Slf4JStopWatch();
		if (id2node.isEmpty()) {
			init(handler);
			// watch.stop("DynamicNodePipeline.addLast().finished");
			return 1L;
		} else {
			Node oldTail = tail;
			long newId = oldTail.id + 1;
			if (newId % 100000 == 0) {
				logger.debug("create node, id:{}", newId);
			}
			Node newTail = new Node(oldTail, null, newId, handler);

			oldTail.next = newTail;
			tail = newTail;
			id2node.put(newId, newTail);
			// watch.stop("DynamicNodePipeline.addLast().finished");
			return newId;
		}
	}

	public synchronized void removeBefore(Node node) {
		long start = head.id;
		long end = node.id;
		// todo 判断handleIndex && handleIndex >= node.id
		if (node != head) {
			node.prev.next = null;
			node.prev = null;
			head = node;
			for (long i = start; i < end; i++) {
				id2node.remove(i);
			}
		}
	}

	public Node getNode(NodeHandler handler) {
		// StopWatch watch = new Slf4JStopWatch();
		if (head == null) {
			return null;
		}
		for (Node current = head; current != null; current = current.next) {
			if (current.nodeHandler == handler) {
				// watch.stop("DynamicNodePipeline.getNode().found");
				return current;
			}
		}
		// watch.stop("DynamicNodePipeline.getNode().not_found");
		return null;
	}

	private void init(NodeHandler handler) {
		Node node = new Node(null, null, 1L, handler);
		head = tail = node;
		id2node.clear();
		id2node.put(1L, node);
	}

	public Map<Long, Node> getId2node() {
		return id2node;
	}

	private final static class Node {
		/**
		 * 上一节点
		 */
		private Node prev;
		/**
		 * 下一节点
		 */
		private Node next;
		/**
		 * 索引
		 */
		private long id;
		/**
		 * handler处理器
		 */
		private NodeHandler nodeHandler;

		public Node(Node prev, Node next, long id, NodeHandler nodeHandler) {
			this.prev = prev;
			this.next = next;
			this.id = id;
			this.nodeHandler = nodeHandler;
		}
	}

	/**
	 * 清理节点
	 * 
	 * @author yang.hao
	 * @since 2011-11-28 下午3:52:15
	 */
	private class NodeRecycler implements Runnable {
		@Override
		public void run() {
			for (;;) {
				try {
					if (id2node.size() > 500) {
						// todo 从哪里剔除?
						removeBefore(tail);
					}
					Thread.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	};

}
