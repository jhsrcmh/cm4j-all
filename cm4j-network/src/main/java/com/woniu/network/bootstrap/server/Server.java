package com.woniu.network.bootstrap.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woniu.network.handler.codec.LittleEndianCoder;
import com.woniu.network.handler.codec.ProtocolDecoder;
import com.woniu.network.handler.server.ProtocolMulticasterHandler;

/**
 * <pre>
 * 由服务器端发消息给客户端
 * 因此服务器端先处理，发送时加码，客户端收到后先解码，然后再处理
 * </pre>
 * 
 * @author yang.hao
 * @since 2011-6-20 上午09:59:40
 * 
 */
public class Server {

	private static final Logger logger = LoggerFactory.getLogger(Server.class);

	public void start() {
		ChannelFactory channelFactory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2));
		ServerBootstrap bootstrap = new ServerBootstrap(channelFactory);

		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("littleEndianCoder", new LittleEndianCoder());
				pipeline.addLast("framerDecoder", new LengthFieldBasedFrameDecoder(10000, 0, 4, 0, 0));

				pipeline.addLast("protocolDecoder", new ProtocolDecoder());
				pipeline.addLast("protocolMulticasterHandler", new ProtocolMulticasterHandler());

				// pipeline.addLast("messagePrintHandler", new
				// MessagePrintHandler());
				return pipeline;
			}
		});

		InetSocketAddress address = new InetSocketAddress(2012);
		bootstrap.bind(address);
		logger.debug("server start at {}", address);
	}
}
