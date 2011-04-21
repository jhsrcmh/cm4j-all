package com.cm4j.test.designpattern.memento.selfbachup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 备忘录模式 
 * 
 * @author yang.hao
 * @since 2011-5-17 下午03:41:33
 * 
 */
public class Client {

	public static final Logger logger = LoggerFactory.getLogger(Client.class);

	public static void main(String[] args) {
		Originator originator = new Originator();
		originator.setState("心情很棒");
		logger.debug("当前心情：{}", originator.getState());
		originator.createMemento();
		originator.changeState();
		logger.debug("当前心情2：{}", originator.getState());
		originator.restortMemento();
		logger.debug("当前心情3：{}", originator.getState());
	}
}
