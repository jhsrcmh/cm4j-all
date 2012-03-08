package com.woniu.network.bootstrap.client;

import java.util.concurrent.SynchronousQueue;

import org.jboss.netty.channel.ChannelPipeline;

import com.woniu.network.protocol.IProtocol;

public class ShortConnectClient extends AbstractClient {

	public ShortConnectClient(String address) {
		super(address);
		super.setReconnect(false);
		super.setCloseChannelAfterWrite(true);
		sendQueue = new SynchronousQueue<IProtocol>();
	}
	
	@Override
	public void pipelinePostProcess(ChannelPipeline pipeline) {
		// do nothing
	}
}
