package com.woniu.network.events;

import java.util.EventObject;

/**
 * 事件
 * 
 * @author yang.hao
 * @since 2011-11-16 下午4:13:48
 */
public class ApplicationEvent extends EventObject {

	private static final long serialVersionUID = 7099057708183571937L;

	/**
	 * constructor
	 * 
	 * @param source
	 *            事件源
	 */
	public ApplicationEvent(Object source) {
		super(source);
	}

}
