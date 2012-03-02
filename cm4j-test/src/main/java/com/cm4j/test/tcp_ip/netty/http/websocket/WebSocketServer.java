package com.cm4j.test.tcp_ip.netty.http.websocket;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebSocket(常连接的一种实现) - http://zh.wikipedia.org/zh-cn/WebSocket
 * 
 * @author yang.hao
 * @since 2011-6-10 下午02:54:52
 * 
 */
public class WebSocketServer {
	private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

	public static void main(String[] args) {
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

		bootstrap.setPipelineFactory(new WebSocketServerPipelineFactory());
		InetSocketAddress inetSocketAddress = new InetSocketAddress(2012);
		bootstrap.bind(inetSocketAddress);
		logger.debug("server boot at {}", inetSocketAddress);
	}
}
