package com.woniu.network.handler.cfg;

import java.util.HashMap;
import java.util.Map;

import com.woniu.network.handler.server.executor.ProtocolExecutor;
import com.woniu.network.handler.server.executor.ProtocolShowExecutor;

/**
 * 协议处理器对应关系配置
 * 
 * @author yang.hao
 * @since 2011-11-23 下午3:42:47
 */
public class ProtocolExecutorMatcher {
	
	private static Map<Integer, ProtocolExecutor> map = new HashMap<Integer, ProtocolExecutor>();
	static {
		map.put(0x1001, new ProtocolShowExecutor());
	}

	public static ProtocolExecutor get(int messageType) {
		return map.get(messageType);
	}
}
