package com.cm4j.test.syntax.nio.netty.core;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * Netty 3.1 中文用户手册 例子
 * 
 * @author yang.hao
 * @since 2011-4-29 上午10:13:09
 * 
 */
@Sharable
public class T3_TimerServerHandler extends SimpleChannelHandler {

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		// 1.5 时间协议服务 - 服务端
		Channel channel = e.getChannel();
		ChannelBuffer time = ChannelBuffers.buffer(4);
		int timeSp = (int) System.currentTimeMillis() / 1000;
		System.out.println("server:" + timeSp);
		time.writeInt(timeSp);

		ChannelFuture future = channel.write(time);

		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				Channel ch = future.getChannel();
				ch.close();
			}
		});

		// 也可这样写
		// future.addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		e.getCause().printStackTrace();
		Channel channel = e.getChannel();
		channel.close();
	}
}
