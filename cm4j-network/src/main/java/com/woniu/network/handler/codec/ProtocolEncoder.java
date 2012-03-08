package com.woniu.network.handler.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woniu.network.exception.ProtocolAnalyzeException;
import com.woniu.network.protocol.ConstructedMessage;

/**
 * 将 {@link IProtocol} 转化为 byte[]
 * 
 * @author yang.hao
 * @since 2011-10-24 下午5:38:27
 */
public class ProtocolEncoder extends OneToOneEncoder {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		try {
			ConstructedMessage protocol = (ConstructedMessage) msg;

//			boolean littleEndian = protocol.getXmlModel().getLittleEndian();
//			ByteOrder order = littleEndian ? ChannelBuffers.LITTLE_ENDIAN : ChannelBuffers.BIG_ENDIAN;
			ChannelBuffer dynamicBuffer = ChannelBuffers.dynamicBuffer(ctx.getChannel().getConfig().getBufferFactory());

//			protocol.write(dynamicBuffer);
			return dynamicBuffer;
		} catch (Exception e) {
			logger.error("protocol encode exception", e);
			throw new ProtocolAnalyzeException(e);
		}
	}
}