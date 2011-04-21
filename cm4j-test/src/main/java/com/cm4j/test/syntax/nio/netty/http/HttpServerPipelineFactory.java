package com.cm4j.test.syntax.nio.netty.http;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;

public class HttpServerPipelineFactory implements ChannelPipelineFactory {

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();

		pipeline.addLast("decoder", new HttpRequestDecoder());
		pipeline.addLast("encoder", new HttpResponseEncoder());

		// Remove the following line if you don't want automatic content
		// compression.
		// pipeline.addLast("deflater", new HttpContentCompressor());
		pipeline.addLast("handler", new HttpRequestHandler());

		return pipeline;
	}

}
