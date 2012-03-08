package com.woniu.network.handler.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * 对{@link ChannelBuffer}进行后续处理
 * 
 * @author yang.hao
 * @since 2011-11-1 下午4:30:22
 */
public class ChannelBufferDealer extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		// 重置channelbuffer, 重设参数length
		ChannelBuffer b = (ChannelBuffer) msg;
		b.setInt(0, b.readableBytes() - 4);
		return b;
	}
}
