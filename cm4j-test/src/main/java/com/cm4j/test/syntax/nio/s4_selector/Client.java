package com.cm4j.test.syntax.nio.s4_selector;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {

	private Logger logger = LoggerFactory.getLogger(getClass());

	ByteBuffer buffer = ByteBuffer.wrap("server，我来了".getBytes());

	public Client() throws Exception {
		InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 1234);
		SocketChannel socketChannel = SocketChannel.open(inetSocketAddress);
		SelectableChannel configureBlocking = socketChannel.configureBlocking(false);

		Selector selector = Selector.open();
		socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		
		logger.debug("client connect to:{}", inetSocketAddress);
		System.out.println(socketChannel.finishConnect());

		while (true) {
			int select = selector.select();
			if (select == 0) {
				continue;
			}

			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			for (SelectionKey selectionKey : selectedKeys) {
				if (selectionKey.isConnectable()) {
					sayHelloToServer(socketChannel);

					socketChannel.register(selector, SelectionKey.OP_WRITE);
				}
				if (selectionKey.isWritable()) {
					
					while (buffer.hasRemaining()){
						socketChannel.write(buffer);
					}
					buffer.clear();
				}

			}

		}
	}

	public static void main(String[] args) throws Exception {
		new Client();
	}

	public void sayHelloToServer(SocketChannel socketChannel) throws IOException {
		buffer.clear();
		buffer.put("Hello,Server!\n".getBytes());
		buffer.flip();
		socketChannel.write(buffer);
	}
}
