package com.cm4j.test.designpattern.decorator.decorat;

import com.cm4j.test.designpattern.decorator.SchoolReport;

public class Decorator extends SchoolReport {

	/**
	 * @uml.property  name="schoolReport"
	 * @uml.associationEnd  
	 */
	private SchoolReport schoolReport;

	public Decorator(SchoolReport schoolReport) {
		this.schoolReport = schoolReport;
	}

	@Override
	public void report() {
		this.schoolReport.report();
	}

	@Override
	public void sign(String name) {
		this.schoolReport.sign(name);
	}

}
