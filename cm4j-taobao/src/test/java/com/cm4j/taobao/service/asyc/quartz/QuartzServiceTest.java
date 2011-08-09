package com.cm4j.taobao.service.asyc.quartz;

import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;

public class QuartzServiceTest {

	public static void main(String[] args) throws SchedulerException {
		QuartzService service = new QuartzService();
		service.addJob(TestJob.class, SimpleScheduleBuilder.repeatSecondlyForever(), null, null, null, null);
		service.startQuartz();
	}
}
