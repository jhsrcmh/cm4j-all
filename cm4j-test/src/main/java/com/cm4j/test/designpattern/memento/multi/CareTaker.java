package com.cm4j.test.designpattern.memento.multi;

public class CareTaker {

	private IMemento memento;

	public IMemento getMemento() {
		return memento;
	}

	public void setMemento(IMemento memento) {
		this.memento = memento;
	}

}
