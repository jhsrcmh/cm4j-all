package com.cm4j.test.syntax.nio.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Reactor implements Runnable {

	final Selector selector;
	final ServerSocketChannel serverSocketChannel;

	public Reactor(int port) throws IOException {
		selector = Selector.open();
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		serverSocketChannel.configureBlocking(false);
		// 只监听accept
		SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		// accept的SelectionKey附件Acceptor对象
		sk.attach(new Acceptor());
	}

	/**
	 * 分发SelectionKey
	 * 
	 * @param k
	 */
	void dispatch(SelectionKey k) {
		Runnable r = (Runnable) k.attachment();
		if (r != null)
			r.run();
	}

	@Override
	public void run() {
		// normally in a new Thread
		while (!Thread.interrupted()) {
			try {
				int num = selector.select();
				if (num == 0)
					continue;

				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> itor = selectedKeys.iterator();
				while (itor.hasNext()) {
					SelectionKey selectionKey = (SelectionKey) itor.next();
					dispatch(selectionKey);
				}
				selectedKeys.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	class Acceptor implements Runnable {

		@Override
		public void run() {
			try {
				SocketChannel c = serverSocketChannel.accept();
				if (c != null) {
					new Handler(selector, c);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	final class Handler implements Runnable {

		public Handler(Selector sel, SocketChannel c) {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}

	}

}
