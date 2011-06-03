package com.cm4j.test.syntax.nio.netty.core;

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
public class T4_TimerClient {

	private static final Logger logger = LoggerFactory.getLogger(T4_TimerClient.class);

	public static void main(String[] args) {
		int port = 2012;
		ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

		ClientBootstrap bootstrap = new ClientBootstrap(factory);
		T4_TimerDecoder decoder = new T4_TimerDecoder();
		T4_TimerClientHandler handler = new T4_TimerClientHandler();

		bootstrap.getPipeline().addLast("decoder", decoder);
		bootstrap.getPipeline().addLast("handler", handler);
		// 客户端重连
		bootstrap.getPipeline().addLast("reconnecor", new T4_Reconnector(bootstrap));

		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", false);
		bootstrap.setOption("child.connectTimeoutMillis", 1000);

		InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", port);
		bootstrap.setOption("remoteAddress", inetSocketAddress);
		ChannelFuture future = bootstrap.connect(inetSocketAddress);

		// Wait until the connection is closed or the connection attempt fails.
		future.getChannel().getCloseFuture().awaitUninterruptibly(5000L);

		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				logger.debug("==========done===========");
			}
		});


		if (!future.isSuccess()) {
			// 如果有重连，此行不能调用，因为此行是在channel可用时关闭资源的，
			// 如果channel不可用，此行会报错
			// Shut down thread pools to exit.
			// bootstrap.releaseExternalResources();
		}
	}
}
