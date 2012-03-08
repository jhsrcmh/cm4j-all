package com.woniu.network.node.event;

import com.woniu.network.events.ApplicationListener;

/**
 * 节点监听器
 * 
 * @author yang.hao
 * @since 2011-12-1 下午5:15:08
 */
public class NodeListener implements ApplicationListener<NodeFinishedEvent> {

	@Override
	public void onEvent(NodeFinishedEvent event) {
		// do something
	}

}
