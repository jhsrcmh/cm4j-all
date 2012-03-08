package com.woniu.network.handler.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woniu.network.exception.ProtocolAnalyzeException;

public class ProtocolDecoder extends OneToOneDecoder {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		try {
			ChannelBuffer buffer = (ChannelBuffer) msg;
//			ConstructedMessage protocol = ProtocolFactory.Holder.instance.createProtocol();
//			protocol.read(buffer);
//			return protocol;
			return null;
		} catch (Exception e) {
			logger.error("protocol decode exception", e);
			throw new ProtocolAnalyzeException(e);
		}
	}

}
