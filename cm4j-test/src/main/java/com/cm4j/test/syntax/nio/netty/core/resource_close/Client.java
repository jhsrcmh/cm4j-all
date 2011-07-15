package com.cm4j.test.syntax.nio.netty.core.resource_close;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cm4j.test.syntax.nio.netty.core.code.TimerClient;

/**
 * 客户端关闭和资源释放
 * 
 * @author yang.hao
 * @since 2011-6-21 下午01:27:44
 *
 */
public class Client {

	private static final Logger logger = LoggerFactory.getLogger(TimerClient.class);

	public static void main(String[] args) {
		int port = 2012;
		ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

		ClientBootstrap bootstrap = new ClientBootstrap(factory);

		InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", port);
		bootstrap.setOption("remoteAddress", inetSocketAddress);
		ChannelFuture future = bootstrap.connect(inetSocketAddress);

		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				logger.debug("client start at {}", future.getChannel().getLocalAddress());
			}
		});

		// 阻塞式的等待，直到ChannelFuture对象返回这个连接操作的成功或失败状态
		future.awaitUninterruptibly();

		// 如果连接失败，我们将打印连接失败的原因。
		if (!future.isSuccess()) {
			logger.error("client connect error", future.getCause());
		}

		// 连接操作结束，我们需要等待并且一直到这个Channel通道返回的closeFuture关闭这个连接。
		// 每一个Channel都可获得自己的closeFuture对象，因此我们可以收到通知并在这个关闭时间点执行某种操作。
		// 即使这个连接操作失败，这个closeFuture仍旧会收到通知，因为这个代表连接的 Channel对象将会在连接操作失败后自动关闭。
		ChannelFuture closeFuture = future.getChannel().getCloseFuture();
		closeFuture.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				logger.debug("client close complete");
			}
		});
		closeFuture.awaitUninterruptibly();

		// 释放ChannelFactory通道工厂使用的资源,包括NIO Secector和线程池在内的所有资源将被自动的关闭和终止
		factory.releaseExternalResources();
	}

}
