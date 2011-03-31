package com.cm4j.test.designpattern.bridge.shape;

import com.cm4j.test.designpattern.bridge.draw.Drawing;

public class Rectangle extends Shape {

	/**
	 * @uml.property  name="x1"
	 */
	private double x1;
	/**
	 * @uml.property  name="y1"
	 */
	private double y1;
	/**
	 * @uml.property  name="x2"
	 */
	private double x2;
	/**
	 * @uml.property  name="y2"
	 */
	private double y2;
	
	public Rectangle(Drawing myDrawing, double x1,double y1,double x2,double y2) {
		super(myDrawing);
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	@Override
	public void draw() {
		myDrawing.drawLine(x1, y1, x1, y2);
		myDrawing.drawLine(x1, y2, x2, y2);
		myDrawing.drawLine(x2, y2, x2, y1);
		myDrawing.drawLine(x2, y1, x1, y1);
	}

}
