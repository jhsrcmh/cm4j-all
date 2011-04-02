package com.cm4j.test.syntax.nio.s2_channel.socketchannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 使用ServerSocketChannel的非阻塞accept()方法 <br />
 * 启动程序，并：telnet 127.0.0.1 1234 进行测试
 * 
 * @author yang.hao
 * @since 2011-3-28 上午09:44:12
 * 
 */
public class ChannelAccept {

	public static final String GENERATING = "Hello,i must go now!";

	public static void main(String[] args) throws IOException, InterruptedException {
		ByteBuffer byteBuffer = ByteBuffer.wrap(GENERATING.getBytes());

		ServerSocketChannel ssc = ServerSocketChannel.open();
		ServerSocket serverSocket = ssc.socket();
		serverSocket.bind(new InetSocketAddress(1234));
		ssc.configureBlocking(false);

		while (true) {
			System.out.println("waiting for connection");
			SocketChannel sc = ssc.accept();
			if (sc == null)
				Thread.sleep(200L);
			else {
				System.out.println("Incoming connection from :" + sc.socket().getRemoteSocketAddress());
				byteBuffer.rewind();
				sc.write(byteBuffer);
				sc.close();
			}
		}
	}
}
