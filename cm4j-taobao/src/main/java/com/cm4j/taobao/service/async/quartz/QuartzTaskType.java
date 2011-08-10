package com.cm4j.taobao.service.async.quartz;

import org.quartz.Job;

import com.cm4j.taobao.service.async.quartz.jobs.IdentityMantain;

/**
 * 定时任务类型
 * 
 * @author yang.hao
 * @since 2011-8-10 下午01:51:17
 * 
 */
public enum QuartzTaskType {
	/**
	 * 身份维持
	 */
	identity_mantain(IdentityMantain.class);

	/**
	 * 对应处理类
	 */
	private Class<? extends Job> handleClazz;

	private QuartzTaskType(Class<? extends Job> handleClazz) {
		this.handleClazz = handleClazz;
	}

	public Class<? extends Job> getHandleClazz() {
		return handleClazz;
	}
}
