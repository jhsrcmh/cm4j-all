package com.cm4j.test.designpattern.builder.build;

import java.util.ArrayList;

import com.cm4j.test.designpattern.builder.model.BMWModel;
import com.cm4j.test.designpattern.builder.model.CarModel;
import com.cm4j.test.designpattern.builder.model.ModelActionEnum;

public class BMWBuilder extends CarBuilder {

    private CarModel bmwModel = new BMWModel();

    @Override
    public void setSequence(ArrayList<ModelActionEnum> sequence) {
        bmwModel.setSequence(sequence);
    }

    @Override
    public CarModel getCarModel() {
        return bmwModel;
    }

}
