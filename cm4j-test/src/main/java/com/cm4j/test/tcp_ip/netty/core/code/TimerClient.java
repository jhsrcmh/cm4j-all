package com.cm4j.test.tcp_ip.netty.core.code;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 本例是从服务端发送消息到客户端，因此服务端加码，客户端解码
 * 
 * @author yang.hao
 * @since 2011-4-29 下午04:58:26
 * 
 */
public class TimerClient {

	private static final Logger logger = LoggerFactory.getLogger(TimerClient.class);

	public static void main(String[] args) {
		int port = 2012;
		ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

		ClientBootstrap bootstrap = new ClientBootstrap(factory);
		TimerDecoder decoder = new TimerDecoder();
		ClientHandler handler = new ClientHandler();

		bootstrap.getPipeline().addLast("decoder", decoder);
		bootstrap.getPipeline().addLast("handler", handler);

		InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", port);
		bootstrap.setOption("remoteAddress", inetSocketAddress);
		ChannelFuture future = bootstrap.connect(inetSocketAddress);

		// Wait until the connection is closed or the connection attempt fails.
		future.getChannel().getCloseFuture().awaitUninterruptibly(5000L);

		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				logger.debug("client start at {}", future.getChannel().getLocalAddress());
			}
		});
	}
}
