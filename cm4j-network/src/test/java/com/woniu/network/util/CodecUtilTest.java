package com.woniu.network.util;

import junit.framework.Assert;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.util.CharsetUtil;
import org.junit.Test;

public class CodecUtilTest {

	@Test
	public void testEncodeString() {
		String source = "Acj好";
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer(64);
		buffer.writeBytes(CodecUtil.encode(source, CharsetUtil.UTF_8));

		String dist = CodecUtil.decode(buffer, CharsetUtil.UTF_8);
		Assert.assertEquals(source, dist);
	}
	
	@Test
	public void testEncodeWString() {
		String source = "Ac好的";
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer(64);
		buffer.writeBytes(CodecUtil.encodeW(source, CharsetUtil.UTF_16));
		
		String dist = CodecUtil.decodeW(buffer, CharsetUtil.UTF_16);
		Assert.assertEquals(source, dist);
	}
}
