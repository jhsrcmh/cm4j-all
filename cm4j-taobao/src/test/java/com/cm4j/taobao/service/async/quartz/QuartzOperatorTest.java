package com.cm4j.taobao.service.async.quartz;

import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;

import com.cm4j.taobao.service.async.quartz.QuartzOperator;

public class QuartzOperatorTest {
	public static void main(String[] args) throws SchedulerException {
		QuartzOperator operator = new QuartzOperator();
		operator.addJob(TestJob.class, SimpleScheduleBuilder.repeatSecondlyForever(), null, null, null, null);
		operator.startQuartz();
	}
}
