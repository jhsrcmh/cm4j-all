package com.cm4j.test.syntax.nio.netty.core;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timeout;
import org.jboss.netty.util.Timer;
import org.jboss.netty.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class T4_Reconnector extends SimpleChannelUpstreamHandler {

	private ClientBootstrap bootstrap;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private final Timer timer = new HashedWheelTimer();

	public T4_Reconnector(ClientBootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}

	public InetSocketAddress getRemoteInet() {
		return (InetSocketAddress) bootstrap.getOption("remoteAddress");
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.channelConnected(ctx, e);
		System.out.println("reconnect channelConnected()");
	}
	
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		logger.debug("disconnect from {}", getRemoteInet());
		super.channelDisconnected(ctx, e);
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		final InetSocketAddress remoteAddress = getRemoteInet();

		timer.newTimeout(new TimerTask() {
			@Override
			public void run(Timeout timeout) throws Exception {
				ChannelFuture future = bootstrap.connect();
				logger.debug("reconnect channel:{}", future.awaitUninterruptibly().getChannel());
			}
		}, 3L, TimeUnit.SECONDS);
	}
}
