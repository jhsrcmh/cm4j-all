package com.cm4j.test.designpattern.decorator;

import com.cm4j.test.designpattern.decorator.decorat.HignScoreDecorator;
import com.cm4j.test.designpattern.decorator.decorat.SortDecorator;

/**
 * 装饰模式 - 动态的给类增加
 * @author yang.hao
 * @since 2011-4-8 下午03:34:21
 *
 */
public class Client {

	public static void main(String[] args) {
		SchoolReport schoolReport = new FourthGradeSchoolReport();
		
		// 加了最高分说明的成绩单
		schoolReport = new HignScoreDecorator(schoolReport);
		
		// 又加了成绩排名的成绩单
		schoolReport = new SortDecorator(schoolReport);

		// 报告
		schoolReport.report();
		
		schoolReport.sign("张三");
	}
}
