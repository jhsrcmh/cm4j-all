package com.cm4j.test.designpattern.bridge.draw;

public class V1Draw extends Drawing {

	@Override
	public void drawCircle(double x, double y, double z) {
		System.out.println("V1 drawCircle:x - " + x + ",y - " + y);
	}

	@Override
	public void drawLine(double x1, double y1, double x2, double y2) {
		System.out.println("V1 drawLine:x1 - " + x1 + ",y1 - " + y1 + " ,x2 - " + x2 + ",y2 - " + y2);
	}
}
