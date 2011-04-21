package com.cm4j.test.designpattern.memento.standard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 备忘录模式 - 将需要保存的变量(如state等)组成pojo（Memento）放到备忘录管理员（Caretaker）那里
 * 
 * @author yang.hao
 * @since 2011-5-17 下午03:41:33
 * 
 */
public class Client {

	public static final Logger logger = LoggerFactory.getLogger(Client.class);

	public static void main(String[] args) {
		Boy boy = new Boy();
		boy.setState("心情很棒");
		logger.debug("当前心情：{}", boy.getState());
		CareTaker careTaker = new CareTaker();
		careTaker.setMemento(boy.createMemento());
		boy.changeState();
		logger.debug("当前心情2：{}", boy.getState());
		boy.restortMemento(careTaker.getMemento());
		logger.debug("当前心情3：{}", boy.getState());
	}
}
