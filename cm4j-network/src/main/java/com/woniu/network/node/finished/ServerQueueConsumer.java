package com.woniu.network.node.finished;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jboss.netty.channel.Channel;

import com.woniu.network.node.Node;
import com.woniu.network.node.Response;

public class ServerQueueConsumer implements QueueConsumer {

	@Override
	public void consume(Queue<Node> queue) {
//		new Thread(n).start();
	};

}

class R implements Runnable {
	private BlockingQueue<Node> queue;
	private Channel channel;

	public R(BlockingQueue<Node> queue, Channel channel) {
		this.queue = queue;
		this.channel = channel;
	};

	@Override
	public void run() {
		while (true) {
			try {
				Node node = queue.poll(3, TimeUnit.SECONDS);
				if (node == null) {
					continue;
				}
				Response response = null;
				try {
					response = node.get();
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
				if (response != null) {
					channel.write(response);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
