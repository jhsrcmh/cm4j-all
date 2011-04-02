package com.cm4j.test.syntax.nio.s4_selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {

	private Logger logger = LoggerFactory.getLogger(getClass());

	ByteBuffer buffer = ByteBuffer.wrap("server,i am coming!".getBytes());

	public Client() throws Exception {
		InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 1234);
		SocketChannel socketChannel = SocketChannel.open(inetSocketAddress);
		socketChannel.configureBlocking(false);

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
				if (selectionKey.isWritable()) {
					sayHelloToServer(socketChannel);
					/*
					 * while (buffer.hasRemaining()){
					 * socketChannel.write(buffer); } buffer.clear();
					 */
				}

				if (selectionKey.isReadable()) {
					buffer.clear();
					while (socketChannel.read(buffer) > 0) {
						buffer.flip();
						while (buffer.hasRemaining()) {
							System.out.println((char) buffer.get());
						}
						buffer.clear();
					}
				}

			}

		}
	}

	public static void main(String[] args) throws Exception {
		new Client();
	}

	public void sayHelloToServer(SocketChannel socketChannel) throws IOException {
		socketChannel.write(buffer);
	}
}
