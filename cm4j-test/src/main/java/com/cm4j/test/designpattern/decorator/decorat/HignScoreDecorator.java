package com.cm4j.test.designpattern.decorator.decorat;

import com.cm4j.test.designpattern.decorator.SchoolReport;

public class HignScoreDecorator extends Decorator {
	
	public HignScoreDecorator(SchoolReport schoolReport) {
		super(schoolReport);
	}
	
	/**
	 * 汇报最高成绩
	 */
	private void reportHighScore(){
		System.out.println("汇报最高成绩");
	}
	
	@Override
	public void report() {
		// 先汇报最高成绩
		this.reportHighScore();
		super.report();
	}
}
