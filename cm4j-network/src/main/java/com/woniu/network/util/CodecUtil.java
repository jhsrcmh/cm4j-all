package com.woniu.network.util;

import java.nio.charset.Charset;

import org.apache.commons.lang.ArrayUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodecUtil {

	private static final Logger logger = LoggerFactory.getLogger(CodecUtil.class);

	/**
	 * 在str转码后再在byte[]增加一位0
	 * 
	 * @param str
	 * @param charset
	 * @return
	 */
	public static byte[] encode(String str, Charset charset) {
		if (str != null) {
			try {
				byte[] b = str.getBytes(charset);
				byte[] c = new byte[b.length + 1];
				System.arraycopy(b, 0, c, 0, b.length);
				c[b.length] = 0;
				return ArrayUtils.add(b, (byte) 0);
			} catch (Exception e) {
				logger.error("encode string {}: {}", e.getClass().getSimpleName(), e.getMessage());
			}
		}

		return new byte[] { 0 };
	}

	public static byte[] encodeW(String str, Charset charset) {
		if (str != null && str.length() > 0) {
			try {
				byte[] b = str.getBytes(charset);
				byte[] c = new byte[b.length + 2]; // 占两位
				System.arraycopy(b, 0, c, 0, b.length);
				c[c.length - 2] = 0;
				c[c.length - 1] = 0;
				return c;
			} catch (Exception e) {
				logger.error("encodeW {}: {}", e.getClass().getSimpleName(), e.getMessage());
			}
		}
		return new byte[] { 0, 0 };
	}

	public static String decode(ChannelBuffer buffer, Charset charset) {
		String result = null;
		int startIndex = buffer.readerIndex();
		byte b = 0;
		do {
			b = buffer.readByte();
			if (0 == b) {
				int endIndex = buffer.readerIndex();
				result = buffer.toString(startIndex, endIndex - startIndex - 1, charset);
			}
		} while (0 != b);
		return result;
	}

	public static String decodeW(ChannelBuffer buffer, Charset charset) {
		String result = null;
		int startIndex = buffer.readerIndex();
		byte prev = 0;
		byte cur = 0;
		do {
			prev = cur;
			cur = buffer.readByte();
			if (0 == prev && 0 == cur) {
				int endIndex = buffer.readerIndex();
				result = buffer.toString(startIndex, endIndex - startIndex - 2, charset);
			}
		} while (0 != prev || 0 != cur);
		return result;
	}
}
