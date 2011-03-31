package com.cm4j.test.designpattern.builder.build;

import java.util.ArrayList;

import com.cm4j.test.designpattern.builder.model.CarModel;
import com.cm4j.test.designpattern.builder.model.ModelActionEnum;

public abstract class CarBuilder {

	public abstract void setSequence(ArrayList<ModelActionEnum> sequence);

	public abstract CarModel getCarModel();

}
