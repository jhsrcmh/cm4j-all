package com.cm4j.taobao.service.async.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cm4j.taobao.api.user.UserAPI;
import com.cm4j.taobao.service.async.quartz.QuartzJobData;
import com.cm4j.taobao.service.async.quartz.QuartzOperator;
import com.taobao.api.ApiException;

public class IdentityMantain implements Job {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		QuartzJobData data = (QuartzJobData) context.getJobDetail().getJobDataMap().get(QuartzOperator.JOBDETAIL_DATA_KEY);
		if (data != null) {
			try {
				UserAPI.user_get("user_id", null, data.getSessionKey());
			} catch (ApiException e) {
				logger.error("定时任务[identity_mantain]执行异常", e);
			}
		}
	}
}
