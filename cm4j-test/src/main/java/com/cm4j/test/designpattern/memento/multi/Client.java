package com.cm4j.test.designpattern.memento.multi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 备忘录模式 - 多属性的备份，采用反射机制
 * 
 * @author yang.hao
 * @since 2011-5-17 下午03:41:33
 * 
 */
public class Client {

	public static final Logger logger = LoggerFactory.getLogger(Client.class);

	public static void main(String[] args) {
		Boy boy = new Boy("心情很棒","state2","state3");
		logger.debug("当前心情：{}/{}/{}", new Object[]{boy.getState(),boy.getState2(),boy.getState3()});
		CareTaker careTaker = new CareTaker();
		careTaker.setMemento(boy.createMemento());
		boy.changeState();
		logger.debug("当前心情2：{}/{}/{}", new Object[]{boy.getState(),boy.getState2(),boy.getState3()});
		boy.restortMemento(careTaker.getMemento());
		logger.debug("当前心情3：{}/{}/{}", new Object[]{boy.getState(),boy.getState2(),boy.getState3()});
	}
}
