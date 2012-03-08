package com.woniu.network.node.finished;

import java.util.Queue;

import com.woniu.network.node.Node;

/**
 * Queue队列消费者
 * 
 * @author yang.hao
 * @since 2011-12-16 上午11:37:04
 */
public interface QueueConsumer {

	public void consume (Queue<Node> queue);
}
