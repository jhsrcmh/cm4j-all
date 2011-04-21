package com.cm4j.test.syntax.nio.netty.core;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

/**
 * 本例是从服务端发送消息到客户端，因此服务端加码，客户端解码
 * 
 * @author yang.hao
 * @since 2011-4-29 下午04:58:26
 * 
 */
public class T4_TimerClient {

	public static void main(String[] args) {
		int port = 2012;
		ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

		ClientBootstrap bootstrap = new ClientBootstrap(factory);
		T4_TimerDecoder decoder = new T4_TimerDecoder();
		T4_TimerClientHandler handler = new T4_TimerClientHandler();
		T4_Reconnector reconnecor = new T4_Reconnector(bootstrap);
		
		bootstrap.getPipeline().addLast("decoder", decoder);
		bootstrap.getPipeline().addLast("handler", handler);
		
		bootstrap.getPipeline().addLast("reconnecor", reconnecor);

		// bootstrap.setOption("tcpNoDelay", true);
		// bootstrap.setOption("keepAlive", false);
		// bootstrap.setOption("child.connectTimeoutMillis", 1000);

		InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", port);
		bootstrap.setOption("remoteAddress", inetSocketAddress);
		ChannelFuture future = bootstrap.connect(inetSocketAddress);

		future.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				System.out.println("done");
			}
		});

		future.getChannel().getCloseFuture().awaitUninterruptibly();
		bootstrap.releaseExternalResources();
	}
}
