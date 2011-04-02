package com.cm4j.test.designpattern.builder;

import java.util.ArrayList;

import com.cm4j.test.designpattern.builder.build.BMWBuilder;
import com.cm4j.test.designpattern.builder.build.BenzBuilder;
import com.cm4j.test.designpattern.builder.model.CarModel;
import com.cm4j.test.designpattern.builder.model.ModelActionEnum;

/**
 * 封装 - 用来屏蔽具体对象的实现细节 上层无需关注顺序
 * 
 * @author yanghao
 * 
 */
public class Director {

    // 作为成员变量的ArrayList或HashMap，在使用前一定要记得clear，防止数据混乱
    private ArrayList<ModelActionEnum> sequence = new ArrayList<ModelActionEnum>();

    private BenzBuilder benzBuilder = new BenzBuilder();

    private BMWBuilder bmwBuilder = new BMWBuilder();

    public CarModel getABenzModel() {
        CarModel carModel = this.benzBuilder.getCarModel();
        this.sequence.clear();
        this.sequence.add(ModelActionEnum.START);
        this.sequence.add(ModelActionEnum.ENGIN_BOOM);
        carModel.setSequence(this.sequence);
        return carModel;
    }

    public CarModel getBBenzModel() {
        CarModel carModel = this.benzBuilder.getCarModel();
        this.sequence.clear();
        this.sequence.add(ModelActionEnum.START);
        this.sequence.add(ModelActionEnum.STOP);
        carModel.setSequence(this.sequence);
        return carModel;
    }

    public CarModel getCBMWModel() {
        CarModel carModel = this.bmwBuilder.getCarModel();
        this.sequence.clear();
        this.sequence.add(ModelActionEnum.ALARM);
        this.sequence.add(ModelActionEnum.ENGIN_BOOM);
        carModel.setSequence(this.sequence);
        return carModel;
    }

    public CarModel getDBMWModel() {
        CarModel carModel = this.bmwBuilder.getCarModel();
        this.sequence.clear();
        this.sequence.add(ModelActionEnum.ALARM);
        this.sequence.add(ModelActionEnum.STOP);
        carModel.setSequence(this.sequence);
        return carModel;
    }
}
