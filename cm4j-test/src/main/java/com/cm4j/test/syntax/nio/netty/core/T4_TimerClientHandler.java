package com.cm4j.test.syntax.nio.netty.core;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Netty 3.1 中文用户手册 例子
 * 
 * @author yang.hao
 * @since 2011-4-29 上午10:35:14
 * 
 */
public class T4_TimerClientHandler extends SimpleChannelUpstreamHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.channelConnected(ctx, e);
		logger.debug("channelConnected():{}", getClass().getSimpleName());
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		logger.debug("messageReceived():{}", getClass().getSimpleName());

		T4_UnixTime time = (T4_UnixTime) e.getMessage();
		logger.debug("client:{}", time.getValue());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		e.getCause().printStackTrace();
		e.getChannel().close();
	}
}
