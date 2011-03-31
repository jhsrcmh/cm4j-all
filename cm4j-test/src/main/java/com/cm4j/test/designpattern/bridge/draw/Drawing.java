package com.cm4j.test.designpattern.bridge.draw;

import java.util.Calendar;

public abstract class Drawing {
	
	abstract public void drawLine (double x1,double y1,double x2,double y2);
	
	abstract public void drawCircle (double x,double y,double z);
	
}
