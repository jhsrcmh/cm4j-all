package com.cm4j.test.designpattern.memento.standard;

public class Boy {

	private String state;

	public void changeState() {
		this.state = "心情不好了";
	}

	public void setState(String state) {
		this.state = state;
	}

	public Memento createMemento() {
		return new Memento(this.state);
	}

	public void restortMemento(Memento _memento) {
		this.setState(_memento.getState());
	}

	public String getState() {
		return state;
	}
}
