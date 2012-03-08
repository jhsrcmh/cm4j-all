package com.woniu.network.handler.client;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.util.Timeout;
import org.jboss.netty.util.Timer;
import org.jboss.netty.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 断线重连
 * 
 * @author yang.hao
 * @since 2011-11-14 下午3:31:17
 */
public class ReconnectHandler extends SimpleChannelUpstreamHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private final int RECONNECT_DELAY = 3;

	private Timer timer;
	private final ClientBootstrap bootstrap;
	private boolean reconnect;

	public ReconnectHandler(Timer timer, ClientBootstrap bootstrap, boolean reconnect) {
		this.timer = timer;
		this.bootstrap = bootstrap;
		this.reconnect = reconnect;
	}

	InetSocketAddress getRemoteAddress() {
		return (InetSocketAddress) bootstrap.getOption("remoteAddress");
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		logger.debug("client channel closed from {}", getRemoteAddress());
		if (this.reconnect) {
			logger.debug("reconnet for seconds:{}", RECONNECT_DELAY);
			timer.newTimeout(new TimerTask() {
				@Override
				public void run(Timeout timeout) throws Exception {
					logger.debug("reconnet to {}", getRemoteAddress());
					ChannelFuture future = bootstrap.connect();

					logger.debug("reconnect to {} result:{}", future.getChannel(), future.isSuccess());
				}
			}, RECONNECT_DELAY, TimeUnit.SECONDS);
		}
	}
}
