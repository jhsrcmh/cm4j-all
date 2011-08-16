package com.cm4j.taobao.service.async.quartz;

import java.text.ParseException;

import org.quartz.SchedulerException;
import org.springframework.scheduling.annotation.Async;

import com.cm4j.taobao.pojo.AsyncTask;

public class QuartzOperatorTest {
	public static void main(String[] args) throws SchedulerException, ParseException {
		QuartzOperator operator = new QuartzOperator();
		QuartzJobData data = new QuartzJobData();
		data.setCron("0 0/20 * * * ?");
		operator.addJob(data);
		operator.startQuartz();
	}
}
