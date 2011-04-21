package com.cm4j.test.designpattern.composite;

import java.util.ArrayList;

public interface IBranch {
	void add(Corp corp);

	ArrayList<Corp> getSubordinateInfo();
}
