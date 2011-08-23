package com.cm4j.taobao.service.async.quartz.jobs;

import org.quartz.JobExecutionException;

import com.cm4j.taobao.api.user.UserAPI;
import com.cm4j.taobao.service.async.quartz.QuartzJobData;
import com.taobao.api.ApiException;

/**
 * cron 身份维持
 * 
 * @author yang.hao
 * @since 2011-8-23 下午6:42:22
 */
public class IdentityMantain extends AbstractJobHandler {

	@Override
	protected void handle(QuartzJobData data) throws JobExecutionException, ApiException {
		if (data != null) {
			UserAPI.user_get("user_id", null, data.getSessionKey());
		}
	}

	@Override
	protected void handleException(Exception e) {
	}

}
