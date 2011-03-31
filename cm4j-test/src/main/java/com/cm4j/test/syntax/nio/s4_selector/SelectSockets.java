package com.cm4j.test.syntax.nio.s4_selector;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * <pre>
 * 简单的反馈 非阻塞 服务端
 * server接受client的数据并原样返回
 * 单个selector监听socket
 * </pre>
 * 
 * @author Ron Hitchens (ron@ronsoft.com)
 */
public class SelectSockets {
	public static int PORT_NUMBER = 1234;

	public static void main(String[] argv) throws Exception {
		new SelectSockets().go(argv);
	}

	public void go(String[] argv) throws Exception {
		int port = PORT_NUMBER;

		if (argv.length > 0) { // Override default listen port
			port = Integer.parseInt(argv[0]);
		}

		System.out.println("Listening on port " + port);

		// 建立Channel 并绑定到端口
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		ServerSocket serverSocket = serverChannel.socket();
		serverSocket.bind(new InetSocketAddress(port));

		// 使设定non-blocking的方式。
		serverChannel.configureBlocking(false);

		// 使用selector
		Selector selector = Selector.open();
		// 向Selector注册Channel及我们有兴趣的事件
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);

		// 不断的轮询
		while (true) {
			// 阻塞，直到我们注册的感兴趣的事件发生
			int n = selector.select();

			// 准备就绪操作集的键的数目为0
			if (n == 0) {
				continue; // nothing to do
			}

			// Selector传回一组SelectionKeys
			// 我们从这些key中的channel()方法中取得我们刚刚注册的channel。
			Iterator<SelectionKey> it = selector.selectedKeys().iterator();

			// Look at each key in the selected set
			while (it.hasNext()) {
				SelectionKey key = it.next();

				// 有新的connect进来?
				if (key.isAcceptable()) {
					ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
					SocketChannel channel = serverSocketChannel.accept();

					registerChannel(selector, channel, SelectionKey.OP_READ);

					// 发消息给client
					sayHello(channel);
				}

				// channel中有数据可以获取?
				if (key.isReadable()) {
					// 接受client数据，处理并返回给client
					readDataFromSocket(key);
				}

				// 一个key被处理完成后，就都被从就绪关键字（ready keys）列表中除去
				// 移除key，它已经被处理了，这样做是为了避免破坏迭代器内部的状态
				it.remove();
			}
		}
	}

	// ----------------------------------------------------------

	/**
	 * Register the given channel with the given selector for the given
	 * operations of interest
	 */
	protected void registerChannel(Selector selector, SelectableChannel channel, int ops) throws Exception {
		if (channel == null) {
			return; // could happen
		}

		// Set the new channel nonblocking
		channel.configureBlocking(false);

		// Register it with the selector
		channel.register(selector, ops);
	}

	// ----------------------------------------------------------

	// Use the same byte buffer for all channels. A single thread is
	// servicing all the channels, so no danger of concurrent acccess.
	private ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

	/**
	 * 从client端(socketChannel)获取数据，接着把数据又回写到client端(socketChannel)
	 * 
	 * @param key
	 *            A SelectionKey object associated with a channel determined by
	 *            the selector to be ready for reading. If the channel returns
	 *            an EOF condition, it is closed here, which automatically
	 *            invalidates the associated key. The selector will then
	 *            de-register the channel on the next select call.
	 */
	protected void readDataFromSocket(SelectionKey key) throws Exception {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		int count;

		buffer.clear(); // Empty buffer

		// Loop while data is available; channel is nonblocking
		while ((count = socketChannel.read(buffer)) > 0) {
			// Send the data; don't assume it goes all at once
			while (buffer.hasRemaining()) {
				buffer.flip();
				byte result = buffer.get();
				System.out.println("server received:" + (char)result);
				buffer.clear();
				buffer.put(result);
				buffer.flip();
				socketChannel.write(buffer);
			}
			
			// WARNING: 上面的代码是有害的，因为它从socketChannel获取数据又回写了，可能会导致死循环，正常情况不应这样写
			buffer.clear(); // Empty buffer
		}

		if (count < 0) {
			// Close channel on EOF, invalidates the key
			socketChannel.close();
		}
	}

	// ----------------------------------------------------------

	/**
	 * Spew a greeting to the incoming client connection.
	 * 
	 * @param channel
	 *            The newly connected SocketChannel to say hello to.
	 */
	private void sayHello(SocketChannel channel) throws Exception {
		buffer.clear();
		buffer.put("Hi client!\r\n".getBytes());
		buffer.flip();

		channel.write(buffer);
	}
}