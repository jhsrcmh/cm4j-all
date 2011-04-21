package com.cm4j.test.syntax.nio.netty.core;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class Server {

	public static void main(String[] args) {
		ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		ServerBootstrap bootstrap = new ServerBootstrap(factory);
		T3_TimerServerHandler handler = new T3_TimerServerHandler();
		
		ChannelPipeline pipeline = bootstrap.getPipeline();
		pipeline.addLast("handler", handler);

		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.bind(new InetSocketAddress(2012));
	}
}
