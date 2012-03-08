package com.woniu.network.util;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.woniu.network.exception.ProtocolParamValidationException;

public class SocketAddressConvertor {

	/**
	 * 将IP:port格式的字符串转换为{@link SocketAddress}
	 * 
	 * @param address
	 * @return
	 * @throws ProtocolParamValidationException
	 */
	public static SocketAddress convert(String address) throws ProtocolParamValidationException {
		if (StringUtils.isBlank(address)) {
			throw new ProtocolParamValidationException("address must be formed as ip:port");
		}
		String[] split = StringUtils.split(address, ":");
		if (split.length != 2) {
			throw new ProtocolParamValidationException("address must be formed as ip:port");
		}
		return new InetSocketAddress(split[0], NumberUtils.toInt(split[1]));
	}
}
