package com.cm4j.test.syntax.nio.netty.core.code;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {

	private static final Logger logger = LoggerFactory.getLogger(Client.class);

	public static void main(String[] args) {
		ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		ClientBootstrap bootstrap = new ClientBootstrap(factory);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("decode", new TimerDecoder());
				pipeline.addLast("handler", new ClientHandler());
				return pipeline;
			}
		});

		InetSocketAddress address = new InetSocketAddress("127.0.0.1", 2012);
		ChannelFuture future = bootstrap.connect(address);

		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				logger.debug("client connect to {}", future.getChannel().getRemoteAddress());
			}
		});
	}
}
