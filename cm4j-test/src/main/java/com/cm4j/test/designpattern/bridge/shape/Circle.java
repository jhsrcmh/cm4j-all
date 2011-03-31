package com.cm4j.test.designpattern.bridge.shape;

import com.cm4j.test.designpattern.bridge.draw.Drawing;

public class Circle extends Shape{

	/**
	 * @uml.property  name="x"
	 */
	private double x;
	/**
	 * @uml.property  name="y"
	 */
	private double y;
	/**
	 * @uml.property  name="z"
	 */
	private double z;
	
	public Circle(Drawing myDrawing,double x,double y,double z) {
		super(myDrawing);
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void draw() {
		myDrawing.drawCircle(x, y, z);
	}
}
