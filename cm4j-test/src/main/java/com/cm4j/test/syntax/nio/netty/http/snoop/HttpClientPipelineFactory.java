package com.cm4j.test.syntax.nio.netty.http.snoop;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

public class HttpClientPipelineFactory implements ChannelPipelineFactory {

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();

		pipeline.addLast("codec", new HttpClientCodec());
		// Remove the following line if you don't want automatic content
		// decompression.
		// 内容解压
		// pipeline.addLast("inflater", new HttpContentDecompressor());
		
		pipeline.addLast("handler", new HttpResponseHandler());

		return pipeline;
	}

}
