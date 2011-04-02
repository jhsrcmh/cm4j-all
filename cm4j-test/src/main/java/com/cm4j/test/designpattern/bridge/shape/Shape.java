package com.cm4j.test.designpattern.bridge.shape;

import com.cm4j.test.designpattern.bridge.draw.Drawing;

public abstract class Shape {

	/**
	 * @uml.property  name="myDrawing"
	 * @uml.associationEnd  
	 */
	protected Drawing myDrawing;
	
	Shape(Drawing myDrawing) {
		this.myDrawing = myDrawing;
	}
	
	abstract public void draw();
}
