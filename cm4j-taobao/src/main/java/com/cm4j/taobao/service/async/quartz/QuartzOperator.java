package com.cm4j.taobao.service.async.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

/**
 * 定时任务操作者
 * 
 * @author yang.hao
 * @since 2011-8-10 上午11:28:14
 * 
 */
@Service
public class QuartzOperator implements DisposableBean {
	
	/**
	 * 常量 - jobDetail数据的key
	 */
	public static final String JOBDETAIL_DATA_KEY = "JOB_DATA_KEY";

	private Scheduler scheduler;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public QuartzOperator() throws SchedulerException {
		scheduler = new StdSchedulerFactory().getScheduler();
	}

	/**
	 * 添加定时任务
	 * 
	 * @param handlerClazz
	 * @param scheduleBuilder
	 * @param groupName
	 * @param params
	 * @param startDate
	 * @param endDate
	 * @throws SchedulerException
	 */
	public void addJob(Class<? extends Job> handlerClazz, ScheduleBuilder<?> scheduleBuilder, String groupName,
			QuartzJobData data, Date startDate, Date endDate) throws SchedulerException {
		JobKey jobKey = new JobKey(handlerClazz.getSimpleName(), groupName);
		scheduler.deleteJob(jobKey);
		JobDetail jobDetail = JobBuilder.newJob(handlerClazz).withIdentity(jobKey).build();

		// 传递参数
		if (data != null) {
			jobDetail.getJobDataMap().put(JOBDETAIL_DATA_KEY, data);
		}

		TriggerBuilder<?> builder = TriggerBuilder.newTrigger().withIdentity(handlerClazz.getSimpleName())
				.forJob(jobDetail).withSchedule(scheduleBuilder);
		if (startDate == null) {
			builder.startAt(new Date());
		} else {
			builder.startAt(startDate);
		}
		if (endDate != null) {
			builder.endAt(endDate);
		}
		Trigger trigger = builder.build();

		scheduler.scheduleJob(jobDetail, trigger);
	}

	public void startQuartz() throws SchedulerException {
		if (scheduler.getJobGroupNames().size() > 0) {
			// 定时任务
			scheduler.start();
		} else {
			logger.warn("no job to do.");
			scheduler.shutdown();
		}
	}

	@Override
	public void destroy() throws Exception {
		logger.info("sched will be shutdown,waiting all jobs finished");
		try {
			scheduler.shutdown(true);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
