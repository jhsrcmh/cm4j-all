package com.cm4j.test.tcp_ip.netty.core.resource_close;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端关闭
 * 
 * @author yang.hao
 * @since 2011-4-29 下午04:57:25
 * 
 */
public class Server {

	private static final Logger logger = LoggerFactory.getLogger(Server.class);

	// 线程安全类
	// 内部持有所有打开状态的Channel通道。
	// 如果一个Channel通道对象被加入到ChannelGroup，如果这个Channel通道被关闭，ChannelGroup将自动移除这个关闭的Channel通道对象。
	// 此外，你还可以对一个ChannelGroup对象内部的所有Channel通道对象执行相同的操作。
	// 例如，当你关闭服务端应用时你可以关闭一个ChannelGroup内部的所有Channel通道对象。
	public static final ChannelGroup allChannels = new DefaultChannelGroup("connect_server");

	public static void main(String[] args) {
		ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		ServerBootstrap bootstrap = new ServerBootstrap(factory);

		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("openHandler", new ChannelOpenHandler());
				return pipeline;
			}
		});

		InetSocketAddress address = new InetSocketAddress(2012);
		Channel channel = bootstrap.bind(new InetSocketAddress(2012));
		logger.debug("server start at {}", address);

		allChannels.add(channel);

		// 一个想象中等待关闭信号的方法。你可以在这里等待某个客户端的关闭信号或者JVM的关闭回调命令。
		// waitForShutdownCommand();
		channel.getCloseFuture().awaitUninterruptibly();
		ChannelGroupFuture groupFuture = allChannels.close();
		groupFuture.awaitUninterruptibly();
		factory.releaseExternalResources();
	}
}
