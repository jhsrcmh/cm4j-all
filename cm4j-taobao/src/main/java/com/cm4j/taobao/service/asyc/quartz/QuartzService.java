package com.cm4j.taobao.service.asyc.quartz;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

/**
 * 定时触发器
 * 
 * @author yang.hao
 * @since 2011-8-5 下午04:33:11
 * 
 */
public class QuartzService {

	private Scheduler scheduler;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public QuartzService() throws SchedulerException {
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
			Map<String, Object> params, Date startDate, Date endDate) throws SchedulerException {
		JobKey jobKey = new JobKey(handlerClazz.getSimpleName(), groupName);
		scheduler.deleteJob(jobKey);
		JobDetail jobDetail = JobBuilder.newJob(handlerClazz).withIdentity(jobKey).build();

		if (params != null) {
			Set<Entry<String, Object>> entrySet = params.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Object value = entry.getValue();
				// jobDetail设置参数
				jobDetail.getJobDataMap().put(key, value);
			}
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
			distroyHook();
		} else {
			logger.warn("no job to do.");
			scheduler.shutdown();
		}
	}

	private void distroyHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				logger.info("sched will be shutdown,waiting all jobs finished");
				try {
					scheduler.shutdown(true);
				} catch (SchedulerException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
