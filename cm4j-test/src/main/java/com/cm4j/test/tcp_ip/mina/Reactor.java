package com.cm4j.test.tcp_ip.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Reactor implements Runnable {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	final Selector selector;
	final ServerSocketChannel serverSocketChannel;

	// step1:setup
	public Reactor(int port) throws IOException {
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		serverSocketChannel.configureBlocking(false);

		// 只监听accept
		selector = Selector.open();
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

	// step2:dispatch loop
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

	// step3:Acceptor
	class Acceptor implements Runnable { // inner

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

	// step4:Handler setup
	final class Handler implements Runnable {

		final SocketChannel socket;
		final SelectionKey sk;
		ByteBuffer input = ByteBuffer.allocate(1024);
		ByteBuffer output = ByteBuffer.allocate(1024);
		static final int READING = 0, SENDING = 1;
		int state = READING;

		public Handler(Selector sel, SocketChannel c) throws IOException {
			socket = c;
			c.configureBlocking(false);
			// Optionally try first read now
			sk = socket.register(sel, 0);
			sk.attach(this);
			sk.interestOps(SelectionKey.OP_READ);
			sel.wakeup();
		}

		boolean inputIsComplete() {
			logger.debug("inputIsComplete()");
			return true;
		}

		boolean outputIsComplete() {
			System.out.println("outputIsComplete()");
			return true;
		}

		void process() {
			System.out.println("process()");
		}

		// step5:Request Handling
		@Override
		public void run() {
			try {
				if (state == READING)
					read();
				else if (state == SENDING)
					send();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		void read() throws IOException {
			socket.read(input);
			if (inputIsComplete()) {
				process();
				state = SENDING;
				sk.interestOps(SelectionKey.OP_WRITE);
			}
		}

		void send() throws IOException {
			socket.write(output);
			if (outputIsComplete())
				sk.cancel();
		}
	}
	
	public static void main(String[] args) throws IOException {
		new Thread(new Reactor(2012)).start();
	}

}
