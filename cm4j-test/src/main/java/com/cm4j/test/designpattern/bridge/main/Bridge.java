package com.cm4j.test.designpattern.bridge.main;

import com.cm4j.test.designpattern.bridge.draw.V1Draw;
import com.cm4j.test.designpattern.bridge.draw.V2Draw;
import com.cm4j.test.designpattern.bridge.shape.Circle;
import com.cm4j.test.designpattern.bridge.shape.Rectangle;
import com.cm4j.test.designpattern.bridge.shape.Shape;

/**
 * 桥接模式 - 抽象和实现解耦
 * 
 * 功能：2个形状形状(方块、圆)，2种动作(v1 v2 画方+画圆都实现)
 *      交叉调用 - v1画方 v1画圆  v2画方 v2画圆
 *  
 * 注意参数不同
 * @author yanghao
 *
 */
public class Bridge {

	public static void main(String[] args) {
		Shape circle = new Circle(new V1Draw(), 10d, 20d, 30d);
		circle.draw();
		
		Shape rectangle = new Rectangle(new V2Draw(),15d,25d,35d,45d);
		rectangle.draw();
	}

}
