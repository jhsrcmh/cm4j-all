package com.woniu.network.bootstrap.client;

import java.net.SocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.woniu.network.exception.ConnectException;
import com.woniu.network.handler.client.ChannelWriteHandler;
import com.woniu.network.handler.client.ReconnectHandler;
import com.woniu.network.handler.codec.ChannelBufferDealer;
import com.woniu.network.handler.codec.LittleEndianCoder;
import com.woniu.network.handler.codec.ProtocolEncoder;
import com.woniu.network.protocol.IProtocol;
import com.woniu.network.util.SocketAddressConvertor;

public abstract class AbstractClient implements Client {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public static final int timeout = 1500;

	final Timer timer = new HashedWheelTimer();

	private SocketAddress address;
	protected boolean reconnect;
	private boolean closeChannelAfterWrite;
	protected BlockingQueue<IProtocol> sendQueue = new LinkedBlockingQueue<IProtocol>(200);

	private ClientBootstrap bootstrap;

	public AbstractClient(String address) {
		this.address = SocketAddressConvertor.convert(address);
		this.bootstrap = bootstrap();
	}

	public abstract void pipelinePostProcess(ChannelPipeline pipeline);

	private ClientBootstrap bootstrap() {
		Preconditions.checkNotNull(this.address, "SocketAddress should not be null");

		ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newFixedThreadPool(1),
				Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2));
		final ClientBootstrap bootstrap = new ClientBootstrap(factory);

		ChannelPipelineFactory pipelineFactory = new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("channelBufferDealer", new ChannelBufferDealer());
				pipeline.addLast("encoder", new ProtocolEncoder());
				pipeline.addLast("channelWriteHandler", new ChannelWriteHandler(sendQueue, closeChannelAfterWrite));
				// todo 根据配置加判断
				pipeline.addLast("littleEndianCoder", new LittleEndianCoder());

				pipeline.addLast("reconnect", new ReconnectHandler(timer, bootstrap, reconnect));

				pipelinePostProcess(pipeline);
				return pipeline;
			}
		};

		bootstrap.setPipelineFactory(pipelineFactory);
		bootstrap.setOption("remoteAddress", this.address);
		return bootstrap;
	}

	@Override
	public void connect() {
		// 建立连接
		ChannelFuture connectionFuture = this.bootstrap.connect(this.address);

		// 阻塞式的等待，直到ChannelFuture对象返回这个连接操作的成功或失败状态
		connectionFuture.awaitUninterruptibly(timeout);
		if (!connectionFuture.isSuccess()) {
			// 连接失败，关闭资源
			logger.error("client connect error", connectionFuture.getCause());
			if (!reconnect) {
				this.bootstrap.getFactory().releaseExternalResources();
			}
			throw new ConnectException("client connect error", connectionFuture.getCause());
		}

		logger.debug("client connect success:{}", connectionFuture.getChannel());
	}

	@Override
	public boolean sendProtocol(final IProtocol protocol) {
		boolean flag = false;
		try {
			flag = this.sendQueue.offer(protocol, timeout, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			logger.error("queue offer error", e);
		}
		return flag;
	}

	public void setReconnect(boolean reconnect) {
		this.reconnect = reconnect;
	}

	public void setCloseChannelAfterWrite(boolean closeChannelAfterWrite) {
		this.closeChannelAfterWrite = closeChannelAfterWrite;
	}
}
