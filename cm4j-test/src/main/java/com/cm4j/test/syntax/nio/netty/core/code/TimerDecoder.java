package com.cm4j.test.syntax.nio.netty.core.code;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 这是T3的另外一种解决方案，更简洁
 * 
 * @author yang.hao
 * @since 2011-4-29 下午02:57:46
 * 
 */
// FrameDecoder总是被注解为“one”
public class TimerDecoder extends ReplayingDecoder {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer, Enum state)
			throws Exception {
		logger.debug("current:{}", getClass().getSimpleName());
		return new UnixTime(buffer.readInt());
	}

	// // 客户端解码 - FrameDecoder
	// @Override
	// protected Object decode(ChannelHandlerContext ctx, Channel channel,
	// ChannelBuffer buffer) throws Exception {
	// logger.debug("current:{}", getClass().getSimpleName());
	// if (buffer.readableBytes() < 4) {
	// return null;
	// }
	// return new T4_UnixTime(buffer.readInt());
	// }

}
