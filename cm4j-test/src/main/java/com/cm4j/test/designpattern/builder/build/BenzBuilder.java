package com.cm4j.test.designpattern.builder.build;

import java.util.ArrayList;

import com.cm4j.test.designpattern.builder.model.BenzModel;
import com.cm4j.test.designpattern.builder.model.CarModel;
import com.cm4j.test.designpattern.builder.model.ModelActionEnum;

public class BenzBuilder extends CarBuilder{

    private CarModel benzModel = new BenzModel();
    
    @Override
    public void setSequence(ArrayList<ModelActionEnum> sequence) {
        benzModel.setSequence(sequence);
    }

    @Override
    public CarModel getCarModel() {
        return benzModel;
    }

}
