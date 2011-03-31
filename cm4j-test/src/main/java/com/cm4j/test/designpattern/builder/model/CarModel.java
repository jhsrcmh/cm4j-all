package com.cm4j.test.designpattern.builder.model;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class CarModel {

	private ArrayList<ModelActionEnum> sequence = new ArrayList<ModelActionEnum>();

	public abstract void start();

	public abstract void stop();

	public abstract void alarm();

	public abstract void engineBoom();

	public void run() {
		for (Iterator<ModelActionEnum> iterator = this.sequence.iterator(); iterator.hasNext();) {
			ModelActionEnum modelAction = (ModelActionEnum) iterator.next();
			if (modelAction == ModelActionEnum.START)
				this.start();
			else if (modelAction == ModelActionEnum.STOP)
				this.stop();
			else if (modelAction == ModelActionEnum.ALARM)
				this.alarm();
			else if (modelAction == ModelActionEnum.ENGIN_BOOM)
				this.engineBoom();
		}
	}

	public void setSequence(ArrayList<ModelActionEnum> sequence) {
		this.sequence = sequence;
	}
}
