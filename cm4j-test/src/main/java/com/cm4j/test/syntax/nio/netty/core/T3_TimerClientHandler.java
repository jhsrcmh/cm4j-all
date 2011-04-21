package com.cm4j.test.syntax.nio.netty.core;

import java.util.Date;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * Netty 3.1 中文用户手册 例子
 * 
 * @author yang.hao
 * @since 2011-4-29 上午10:35:14
 * 
 */
public class T3_TimerClientHandler extends SimpleChannelHandler {

	private final ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		// 1.6 时间协议服务客户端
		ChannelBuffer buf = (ChannelBuffer) e.getMessage();
		buffer.writeBytes(buf);

		if (buffer.readableBytes() >= 4) {
			Long mill = buffer.readInt() * 1000L;
			System.out.println("client:" + mill);
			System.out.println(new Date(mill));
			e.getChannel().close();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		e.getCause().printStackTrace();
		e.getChannel().close();
	}
}
