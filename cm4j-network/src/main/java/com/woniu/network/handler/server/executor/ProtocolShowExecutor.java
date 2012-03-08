package com.woniu.network.handler.server.executor;

import org.jboss.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woniu.network.protocol.ConstructedMessage;
import com.woniu.network.protocol.ProtocolMessage;

public class ProtocolShowExecutor implements ProtocolExecutor {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void execute(Channel channel, ConstructedMessage protocol) {
//		ProtocolMessage message = protocol.getMessages().get(0);
//		logger.debug(">>>>accountId:{}", message.getProtocolField("accountId"));
	}

}
