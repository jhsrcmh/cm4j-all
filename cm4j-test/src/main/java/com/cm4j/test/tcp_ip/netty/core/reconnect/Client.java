package com.cm4j.test.tcp_ip.netty.core.reconnect;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cm4j.test.tcp_ip.netty.core.code.TimerClient;

public class Client {

	private static final Logger logger = LoggerFactory.getLogger(TimerClient.class);

	public static void main(String[] args) {
		int port = 2012;
		ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		
		ClientBootstrap bootstrap = new ClientBootstrap(factory);
		// 客户端重连
		bootstrap.getPipeline().addLast("reconnecor", new Reconnector(bootstrap));
		
		InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", port);
		bootstrap.setOption("remoteAddress", inetSocketAddress);
		ChannelFuture future = bootstrap.connect(inetSocketAddress);

		// Wait until the connection is closed or the connection attempt fails.
		future.getChannel().getCloseFuture().awaitUninterruptibly(5000L);

		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				logger.debug("client start at {}" , future.getChannel().getLocalAddress());
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
