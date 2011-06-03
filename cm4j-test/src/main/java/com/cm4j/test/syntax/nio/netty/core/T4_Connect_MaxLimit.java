package com.cm4j.test.syntax.nio.netty.core;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端设最多客户端连接数 - 2
 * 
 * @author yang.hao
 * @since 2011-6-10 下午05:09:27
 * 
 */
public class T4_Connect_MaxLimit extends SimpleChannelHandler{

	private int counter;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void connectRequested(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.connectRequested(ctx, e);
		logger.debug("connectRequested():{}", getClass().getSimpleName());
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		logger.debug("channelConnnected():{}" ,getClass().getSimpleName());
		if (counter < 2) {
			counter++;
			logger.debug("创建连接,counter:{}",counter);
			ctx.sendUpstream(e);
		} else {
			logger.error("超过最大连接数,{}/{}", counter, 2);
			Channels.disconnect(ctx.getChannel());
		}
	}
}
