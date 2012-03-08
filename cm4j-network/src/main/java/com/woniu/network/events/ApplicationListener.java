package com.woniu.network.events;

import java.util.EventListener;

/**
 * 监听器 - E 代表此监听器只处理此类事件
 * 
 * @author yang.hao
 * @since 2011-11-16 下午4:13:21
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

	/**
	 * 事件发生时触发
	 * 
	 * @param E
	 */
	public void onEvent(E event);
}
