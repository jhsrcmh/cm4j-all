package com.woniu.network.handler.codec;

import java.nio.ByteOrder;

import org.jboss.netty.buffer.HeapChannelBufferFactory;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class LittleEndianCoder extends SimpleChannelHandler {
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		ctx.getChannel().getConfig().setBufferFactory(HeapChannelBufferFactory.getInstance(ByteOrder.LITTLE_ENDIAN));
		super.channelConnected(ctx, e);
	}
}
