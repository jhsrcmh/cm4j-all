package com.cm4j.taobao.service.async.quartz;

import org.springframework.context.ApplicationContext;

import com.cm4j.taobao.service.async.quartz.data.QuartzJobData;
import com.cm4j.taobao.service.async.quartz.jobs.AbstractJobHandler;

public class TestJob extends AbstractJobHandler {

	@Override
	protected String handle(QuartzJobData quartzJobData,ApplicationContext ctx) throws Exception {
		System.out.println("==========AAA==========");
		return null;
	}

	@Override
	protected void handleException(Exception e) {
	}
}
