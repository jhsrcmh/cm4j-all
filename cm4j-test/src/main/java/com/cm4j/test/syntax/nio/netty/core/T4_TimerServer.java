package com.cm4j.test.syntax.nio.netty.core;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * 本例是从服务端发送消息到客户端，因此服务端加码，客户端解码
 * @author yang.hao
 * @since 2011-4-29 下午04:57:25
 *
 */
public class T4_TimerServer {

	public static void main(String[] args) {
		ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		ServerBootstrap bootstrap = new ServerBootstrap(factory);
		
		bootstrap.getPipeline().addLast("encoder", new T4_TimerEncoder());
		bootstrap.getPipeline().addLast("connect_limit", new T4_Connect_MaxLimit());
		bootstrap.getPipeline().addLast("handler", new T4_TimerServerHandler());

		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", false);
		bootstrap.setOption("connectTimeoutMillis", 1000);

		bootstrap.bind(new InetSocketAddress(2012));
	}
}
