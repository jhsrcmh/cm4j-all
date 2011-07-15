package com.cm4j.test.syntax.nio.netty.core.reconnect;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timeout;
import org.jboss.netty.util.Timer;
import org.jboss.netty.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端重连
 * 
 * @author yang.hao
 * @since 2011-6-10 下午05:05:04
 *
 */
public class Reconnector extends SimpleChannelUpstreamHandler {

	private ClientBootstrap bootstrap;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private final Timer timer = new HashedWheelTimer();

	public Reconnector(ClientBootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}

	public InetSocketAddress getRemoteInet() {
		return (InetSocketAddress) bootstrap.getOption("remoteAddress");
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		logger.debug("channel connected:{}",ctx.getChannel());
	}
	
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		timer.newTimeout(new TimerTask() {
			@Override
			public void run(Timeout timeout) throws Exception {
				ChannelFuture future = bootstrap.connect(getRemoteInet());
				logger.debug("reconnect channel:{}", future.getChannel());
			}
		}, 3L, TimeUnit.SECONDS);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		logger.error("exception information ==>",e.getCause());
	}
}
