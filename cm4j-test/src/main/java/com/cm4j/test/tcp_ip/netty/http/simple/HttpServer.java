package com.cm4j.test.tcp_ip.netty.http.simple;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.jboss.netty.util.CharsetUtil;

import com.google.common.base.Charsets;

public class HttpServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("decoder", new HttpRequestDecoder());
				pipeline.addLast("encoder", new HttpResponseEncoder());

				pipeline.addLast("handler", new RequestHandler());
				return pipeline;
			}
		});

		bootstrap.bind(new InetSocketAddress(8001));
	}

	static class RequestHandler extends SimpleChannelUpstreamHandler {
		@Override
		public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
			// 参数处理
			HttpRequest request = (HttpRequest) e.getMessage();
			QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());
			Map<String, List<String>> parameters = queryStringDecoder.getParameters();
			if (parameters!= null){
				for (Entry<String, List<String>> entry : parameters.entrySet()) {
					String key = entry.getKey();
					List<String> values = entry.getValue();
					for (String value : values){
						System.out.println(key + "=" + value);
					}
				}
			}
			
			if (request.isChunked()){
				HttpChunk chunk = (HttpChunk) e.getMessage();
				String result = chunk.getContent().toString(CharsetUtil.UTF_8);
				System.out.println(result);
			}
				
			// response 返回
			HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
			response.setContent(ChannelBuffers.copiedBuffer("好，收到了", Charsets.UTF_8));

			ChannelFuture future = Channels.write(ctx.getChannel(), response);
			// 关闭
			future.addListener(ChannelFutureListener.CLOSE);
		}

	}
}
