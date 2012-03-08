package com.woniu.network.handler.server;

import java.util.concurrent.atomic.AtomicLong;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woniu.network.exception.ExecutorNotFoundException;
import com.woniu.network.handler.cfg.ProtocolExecutorMatcher;
import com.woniu.network.handler.server.executor.ProtocolExecutor;
import com.woniu.network.protocol.ConstructedMessage;
import com.woniu.network.protocol.ProtocolMessage;

/**
 * protocol分发器
 * 
 * @author yang.hao
 * @since 2011-11-23 下午2:40:09
 */
public class ProtocolMulticasterHandler extends SimpleChannelUpstreamHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private AtomicLong counter = new AtomicLong(0L);

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		if (counter.incrementAndGet() % 100 == 0) {
			logger.debug("===============server received message count:{}===============", counter.get());
		}
		
		ConstructedMessage protocol = (ConstructedMessage) e.getMessage();
//		ProtocolMessage message = protocol.getMessages().get(0);
		ProtocolMessage message = null;
		Integer type = (Integer) message.getProtocolFieldValue("messageType");
		if (type == null || type == 0) {
			throw new ExecutorNotFoundException(
					"illegal messageType, can not find corresponding executor, messageType=" + type);
		}

		ProtocolExecutor executor = ProtocolExecutorMatcher.get(type);
		if (executor == null) {
			logger.error("not found corresponding executor,type=" + type);
			throw new ExecutorNotFoundException("not found corresponding executor,type=" + type);
		} else {
			executor.execute(ctx.getChannel(), protocol);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		logger.error("server received exception", e.getCause());
		super.exceptionCaught(ctx, e);
	}
}
