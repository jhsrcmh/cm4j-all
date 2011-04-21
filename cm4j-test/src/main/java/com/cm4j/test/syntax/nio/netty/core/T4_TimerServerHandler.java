package com.cm4j.test.syntax.nio.netty.core;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Netty 3.1 中文用户手册 例子
 * 
 * @author yang.hao
 * @since 2011-4-29 上午10:13:09
 * 
 */
@Sharable
public class T4_TimerServerHandler extends SimpleChannelHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		logger.debug("current:{}" ,getClass().getSimpleName());
		
		// 1.5 时间协议服务 - 服务端
		Channel channel = e.getChannel();
		ChannelFuture future = channel.write(new T4_UnixTime(888886666));

//		future.addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		e.getCause().printStackTrace();
		Channel channel = e.getChannel();
		channel.close();
	}
}
