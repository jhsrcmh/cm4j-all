package com.cm4j.test.syntax.nio.netty.core.resource_close;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 为了记录所有打开的socket，将他们放入 ChannelGroup
 * 
 * @author yang.hao
 * @since 2011-6-21 下午01:38:32
 * 
 */
public class ChannelOpenHandler extends SimpleChannelUpstreamHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		logger.debug("server channel open:{}", ctx.getChannel());
		Server.allChannels.add(ctx.getChannel());
	}
}
