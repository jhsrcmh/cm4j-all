package com.cm4j.test.designpattern.memento.multi;

import java.util.HashMap;

public class Boy {

	private String state;
	private String state2;
	private String state3;

	public void changeState() {
		this.state = "心情不好了";
		this.state2 = "state2_____change";
		this.state3 = "state3_____change";
	}

	public Boy(String state, String state2, String state3) {
		super();
		this.state = state;
		this.state2 = state2;
		this.state3 = state3;
	}

	public IMemento createMemento() {
		return new Memento(BeanUtils.backupProp(this));
	}

	public void restortMemento(IMemento _memento) {
		BeanUtils.restoreProp(this, ((Memento) _memento).getMap());
	}

	public String getState2() {
		return state2;
	}

	public void setState2(String state2) {
		this.state2 = state2;
	}

	public String getState3() {
		return state3;
	}

	public void setState3(String state3) {
		this.state3 = state3;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/**
	 * 考虑对象的安全问题，私有内部类，且实现了窄接口IMemento
	 * 
	 * @author yang.hao
	 * @since 2011-5-20 上午10:48:57
	 * 
	 */
	private class Memento implements IMemento {
		private HashMap<String, Object> map;

		public Memento(HashMap<String, Object> map) {
			super();
			this.map = map;
		}

		public HashMap<String, Object> getMap() {
			return map;
		}

	}

}
