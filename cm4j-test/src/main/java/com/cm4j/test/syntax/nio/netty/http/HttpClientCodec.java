package com.cm4j.test.syntax.nio.netty.http;

import java.util.Queue;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelDownstreamHandler;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.HttpMessage;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpRequestEncoder;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseDecoder;
import org.jboss.netty.util.internal.LinkedTransferQueue;

public class HttpClientCodec implements ChannelUpstreamHandler, ChannelDownstreamHandler {

	/** A queue that is used for correlating a request and a response. */
	final Queue<HttpMethod> queue = new LinkedTransferQueue<HttpMethod>();

	/** If true, decoding stops (i.e. pass-through) */
	volatile boolean done;

	private final HttpRequestEncoder encoder = new Encoder();
	private final HttpResponseDecoder decoder;

	/**
	 * Creates a new instance with the default decoder options (
	 * {@code maxInitialLineLength (4096} , {@code maxHeaderSize (8192)}, and
	 * {@code maxChunkSize (8192)}).
	 */
	public HttpClientCodec() {
		this(4096, 8192, 8192);
	}

	/**
	 * Creates a new instance with the specified decoder options.
	 */
	public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize) {
		decoder = new Decoder(maxInitialLineLength, maxHeaderSize, maxChunkSize);
	}

	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
		decoder.handleUpstream(ctx, e);
	}

	public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
		encoder.handleDownstream(ctx, e);
	}

	private final class Encoder extends HttpRequestEncoder {
		Encoder() {
			super();
		}

		@Override
		protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
			if (msg instanceof HttpRequest && !done) {
				queue.offer(((HttpRequest) msg).getMethod());
			}
			return super.encode(ctx, channel, msg);
		}
	}

	private final class Decoder extends HttpResponseDecoder {

		Decoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize) {
			super(maxInitialLineLength, maxHeaderSize, maxChunkSize);
		}

		@Override
		protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer, State state)
				throws Exception {
			if (done) {
				return buffer.readBytes(actualReadableBytes());
			} else {
				return super.decode(ctx, channel, buffer, state);
			}
		}

		@Override
		protected boolean isContentAlwaysEmpty(HttpMessage msg) {
			final int statusCode = ((HttpResponse) msg).getStatus().getCode();
			if (statusCode == 100) {
				// 100-continue response should be excluded from paired
				// comparison.
				return true;
			}

			// Get the method of the HTTP request that corresponds to the
			// current response.
			HttpMethod method = queue.poll();

			char firstChar = method.getName().charAt(0);
			switch (firstChar) {
			case 'H':
				// According to 4.3, RFC2616:
				// All responses to the HEAD request method MUST NOT include a
				// message-body, even though the presence of entity-header
				// fields
				// might lead one to believe they do.
				if (HttpMethod.HEAD.equals(method)) {
					// Interesting edge case:
					// Zero-byte chunk will appear if Transfer-Encoding of the
					// response is 'chunked'. This is probably because of the
					// trailing headers.
					return !msg.isChunked();
				}
				break;
			case 'C':
				// Successful CONNECT request results in a response with empty
				// body.
				if (statusCode == 200) {
					if (HttpMethod.CONNECT.equals(method)) {
						// Proxy connection established - Not HTTP anymore.
						done = true;
						queue.clear();
						return true;
					}
				}
				break;
			}

			return super.isContentAlwaysEmpty(msg);
		}
	}

}
