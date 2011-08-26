package com.cm4j.taobao.service.async.quartz;

import java.text.ParseException;

import org.quartz.SchedulerException;

import com.cm4j.taobao.pojo.AsyncTask.DATE_ENUM;
import com.cm4j.taobao.service.async.quartz.data.QuartzJobData;

public class QuartzOperatorTest {
	public static void main(String[] args) throws SchedulerException, ParseException {
		QuartzOperator operator = new QuartzOperator();

		QuartzJobData data = new QuartzJobData();
		data.setCron("0 0/20 * * * ?");
		data.setHandlerClazz(TestJob.class);
		data.setTaskId(1L);
		data.setUserId(180812L);
		data.setSessionKey(null);
		data.setJsonData(null);
		data.setStartDate(DATE_ENUM.NOW.apply());
		data.setEndDate(DATE_ENUM.FOREVER.apply());

		operator.addJob(data);
		operator.startQuartz();
	}
}
