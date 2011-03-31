package com.cm4j.test.designpattern.decorator.decorat;

import com.cm4j.test.designpattern.decorator.SchoolReport;

public class SortDecorator extends Decorator {

	public SortDecorator(SchoolReport schoolReport) {
		super(schoolReport);
	}

	/**
	 * 告诉老爸学校排名情况
	 */
	private void reportSort() {
		System.out.println("学校排名情况");
	}

	@Override
	public void report() {
		super.report();
		this.reportSort();
	}
}
