package com.cm4j.taobao.service.async.quartz;

import java.text.ParseException;

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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.cm4j.taobao.pojo.AsyncTask.DATE_ENUM;
import com.cm4j.taobao.service.async.quartz.data.QuartzJobData;

/**
 * 定时任务操作者
 * 
 * @author yang.hao
 * @since 2011-8-10 上午11:28:14
 * 
 */
@Service
public class QuartzOperator implements ApplicationContextAware, DisposableBean {

	/**
	 * 常量 - jobDetail数据key - 业务数据
	 */
	public static final String JOBDETAIL_DATA_KEY = "JOB_DATA_KEY";

	/**
	 * 常量 - jobDetail数据key - Spring 上下文
	 */
	public static final String APPLICATION_CONTEXT = "APPLICATION_CONTEXT";

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
		JobKey jobKey = getJobKey(handlerClazz, data.getTaskId());

		// 删除旧任务
		deleteJob(jobKey);
		JobDetail jobDetail = JobBuilder.newJob(handlerClazz).withIdentity(jobKey).build();

		jobDetail.getJobDataMap().put(JOBDETAIL_DATA_KEY, data);
		jobDetail.getJobDataMap().put(APPLICATION_CONTEXT, ctx);

		TriggerBuilder<?> builder = TriggerBuilder.newTrigger()
				.withIdentity(handlerClazz.getSimpleName() + data.getTaskId()).forJob(jobDetail)
				.withSchedule(CronScheduleBuilder.cronSchedule(data.getCron()));
		if (data.getStartDate() == null) {
			builder.startAt(DATE_ENUM.NOW.apply());
		} else {
			builder.startAt(data.getStartDate());
		}
		if (data.getEndDate() != null) {
			builder.endAt(data.getEndDate());
		}
		Trigger trigger = builder.build();

		scheduler.scheduleJob(jobDetail, trigger);
		
		// 触发任务
		 scheduler.triggerJob(jobKey);
	}

	/**
	 * 启动任务
	 * 
	 * @throws SchedulerException
	 */
	public void startQuartz() throws SchedulerException {
		if (scheduler.getJobGroupNames().size() > 0) {
			// 定时任务
			scheduler.start();
		} else {
			logger.warn("no job to do.");
			scheduler.shutdown();
		}
	}

	public void deleteJob(JobKey jobKey) {
		// 删除任务
		try {
			scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			logger.error("删除任务异常,jobKey:" + jobKey, e);
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

	public JobKey getJobKey(Class<? extends Job> handlerClazz, Long taskId) {
		return new JobKey(handlerClazz.getSimpleName() + "-" + taskId, "TOP");
	}

	private ApplicationContext ctx;

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx = ctx;
	}

}
