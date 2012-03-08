package com.woniu.network.bootstrap.client;

import com.woniu.network.exception.ConnectException;
import com.woniu.network.protocol.IProtocol;

/**
 * 客户端
 * 
 * @author yang.hao
 * @since 2011-11-18 下午4:59:49
 */
public interface Client {

	/**
	 * 连接
	 */
	public abstract void connect() throws ConnectException;
	
	/**
	 * 发送协议
	 * 
	 * @param protocol
	 * @return
	 */
	public abstract boolean sendProtocol(final IProtocol protocol);

}