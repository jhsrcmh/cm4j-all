package com.cm4j.test.designpattern.decorator;

public class FourthGradeSchoolReport extends SchoolReport {

	@Override
	public void report() {
		System.out.println("4年级成绩单报告中...");
	}

	@Override
	public void sign(String name) {
		System.out.println("签名：" + name);
	}

}
