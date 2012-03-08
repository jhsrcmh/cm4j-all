package com.woniu.network.node.event;

import com.woniu.network.events.ApplicationEvent;

/**
 * 节点处理完成事件
 * 
 * @author yang.hao
 * @since 2011-12-1 下午5:15:17
 */
public class NodeFinishedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public NodeFinishedEvent(Object source) {
		super(source);
	}

}
