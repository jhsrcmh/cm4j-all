package com.cm4j.taobao.service.async.quartz;

import java.text.ParseException;

import org.quartz.SchedulerException;

public class QuartzOperatorTest {
	public static void main(String[] args) throws SchedulerException, ParseException {
		QuartzOperator operator = new QuartzOperator();
		QuartzJobData data = new QuartzJobData();
		data.setCron("0 0/20 * * * ?");
		operator.addJob(data);
		operator.startQuartz();
	}
}
