package com.cm4j.test.tcp_ip.netty.core.reconnect;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 本例是从服务端发送消息到客户端，因此服务端加码，客户端解码
 * 
 * @author yang.hao
 * @since 2011-4-29 下午04:57:25
 * 
 */
public class Server {

	private static final Logger logger = LoggerFactory.getLogger(Server.class);

	public static void main(String[] args) {
		ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		ServerBootstrap bootstrap = new ServerBootstrap(factory);

		InetSocketAddress address = new InetSocketAddress(2012);
		bootstrap.bind(new InetSocketAddress(2012));
		logger.debug("server start at {}", address);
	}
}
