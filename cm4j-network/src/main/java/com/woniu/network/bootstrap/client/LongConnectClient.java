package com.woniu.network.bootstrap.client;

import java.util.concurrent.LinkedBlockingQueue;

import org.jboss.netty.channel.ChannelPipeline;

import com.woniu.network.protocol.IProtocol;

public class LongConnectClient extends AbstractClient {

	public LongConnectClient(String address) {
		super(address);
		super.setReconnect(true);
		super.setCloseChannelAfterWrite(false);
		sendQueue = new LinkedBlockingQueue<IProtocol>(500);
	}
	
	public void setReconnect(boolean reconnect){
		super.setReconnect(reconnect);
	}
	
	@Override
	public void pipelinePostProcess(ChannelPipeline pipeline) {
		// do nothing
	}
}
