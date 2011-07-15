package com.cm4j.test.syntax.nio.netty.core.code;

import org.jboss.netty.channel.Channel;
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
public class ServerHandler extends SimpleChannelHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		logger.debug("channelConnnected():{}", getClass().getSimpleName());

		Channel channel = e.getChannel();
		// 加码是在write方法(write事件)加码的
		// *** 因此在Server端的encoder的位置则无所谓 ***
		channel.write(new UnixTime(888886666));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		logger.error("exception caught -->", e.getCause());
		Channel channel = e.getChannel();
		channel.close();
	}
}
