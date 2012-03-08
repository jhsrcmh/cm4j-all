package com.woniu.network.handler.server.executor;

import org.jboss.netty.channel.Channel;

import com.woniu.network.protocol.ConstructedMessage;

/**
 * 协议处理器
 * 
 * @author yang.hao
 * @since 2011-11-23 下午4:14:51
 */
public interface ProtocolExecutor {

	public void execute(Channel channel, ConstructedMessage protocol);
}
