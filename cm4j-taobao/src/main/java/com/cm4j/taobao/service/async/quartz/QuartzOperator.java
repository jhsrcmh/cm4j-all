package com.cm4j.taobao.service.async.quartz;

import java.text.ParseException;

import org.apache.commons.lang.RandomStringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

import com.cm4j.taobao.pojo.AsyncTask;

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
	 * @param data
	 * @throws SchedulerException
	 * @throws ParseException
	 *             cron 格式不正常
	 */
	public void addJob(QuartzJobData data) throws SchedulerException, ParseException {
		if (data == null) {
			throw new IllegalArgumentException("异步任务data参数不允许为空");
		}
		
		Class<? extends Job> handlerClazz = data.getHandlerClazz();
		JobKey jobKey = new JobKey(handlerClazz.getSimpleName() + RandomStringUtils.randomAlphanumeric(10));
		scheduler.deleteJob(jobKey);
		JobDetail jobDetail = JobBuilder.newJob(handlerClazz).withIdentity(jobKey).build();
		
		jobDetail.getJobDataMap().put(JOBDETAIL_DATA_KEY, data);

		TriggerBuilder<?> builder = TriggerBuilder.newTrigger().withIdentity(handlerClazz.getSimpleName())
				.forJob(jobDetail).withSchedule(CronScheduleBuilder.cronSchedule(data.getCron()));
		if (data.getStartDate() == null) {
			builder.startAt(AsyncTask.DATE_NOW);
		} else {
			builder.startAt(data.getStartDate());
		}
		if (data.getEndDate() != null) {
			builder.endAt(data.getEndDate());
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
