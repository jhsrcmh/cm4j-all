package com.cm4j.test.designpattern.decorator;

import com.cm4j.test.designpattern.decorator.decorat.HignScoreDecorator;
import com.cm4j.test.designpattern.decorator.decorat.SortDecorator;

public class Client {

	public static void main(String[] args) {
		SchoolReport schoolReport = new FourthGradeSchoolReport();
		
		schoolReport = new HignScoreDecorator(schoolReport);
		
		// 加了最高分说明的成绩单
		schoolReport = new SortDecorator(schoolReport);
		
		// 又加了成绩排名的成绩单
		schoolReport.report();
		
		schoolReport.sign("张三");
	}
}
