package com.cm4j.test.designpattern.memento.selfbachup;

/**
 * 发起人自主备份和恢复 - 全状态备份 - clone() 方式
 * 
 * clone()方式适合于简单场景的备份
 * 
 * @author yang.hao
 * @since 2011-5-18 上午11:20:56
 * 
 */
public class Originator implements Cloneable {

	private Originator backup;
	private String state;

	public void changeState() {
		this.state = "心情不好了";
	}

	public void setState(String state) {
		this.state = state;
	}

	public void createMemento() {
		backup = this.clone();
	}

	public void restortMemento() {
		// 在恢复前应该进行断言，防止控制针
		this.setState(backup.getState());
	}

	public String getState() {
		return state;
	}

	@Override
	public Originator clone() {
		try {
			return (Originator) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
