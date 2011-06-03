package com.cm4j.test.syntax.nio.netty.core;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Sharable
public class T4_TimerEncoder extends OneToOneEncoder {

	private Logger logger = LoggerFactory.getLogger(getClass());
	// @Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		T4_UnixTime time = (T4_UnixTime) e.getMessage();
		ChannelBuffer buffer = ChannelBuffers.buffer(4);
		buffer.writeInt(time.getValue());
		Channels.write(ctx, e.getFuture(), buffer);
	}
	
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		logger.debug("encode:{}" ,getClass().getSimpleName());
		
		T4_UnixTime time = (T4_UnixTime) msg;
		ChannelBuffer buffer = ChannelBuffers.buffer(4);
		buffer.writeInt(time.getValue());
		return buffer;
	}
}
