package com.cm4j.taobao.service.async.quartz;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.cm4j.taobao.dao.CronTaskDao;
import com.cm4j.taobao.pojo.CronTask;
import com.cm4j.taobao.pojo.UserInfo;
import com.cm4j.taobao.service.async.quartz.jobs.IdentityMantain;

/**
 * 定时触发器
 * 
 * @author yang.hao
 * @since 2011-8-5 下午04:33:11
 * 
 */
@Service
public class QuartzService {

	@Autowired
	private CronTaskDao cronTaskDao;
	@Autowired
	private QuartzOperator quartzOperator;
	@Autowired
	private TaskExecutor taskExecutor;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 从数据库中获取需要执行的任务并执行
	 */
	public void startJobs() {
		taskExecutor.execute(new StartJobsRunnable());
	}
	
	public class StartJobsRunnable implements Runnable {
		@Override
		public void run() {
			// todo 任务太多 分页？
			List<Object[]> results = cronTaskDao.getCronTasks();
			if (CollectionUtils.isEmpty(results)) {
				logger.warn("未查询到可执行的定时任务");
				return;
			}
			for (Object[] result : results) {
				CronTask cronTask = (CronTask) result[0];
				UserInfo userInfo = (UserInfo) result[1];

				QuartzJobData data = new QuartzJobData();
				data.setJsonData(cronTask.getTaskData());
				data.setUserId(userInfo.getUserId());
				data.setSessionKey(userInfo.getSessionKey());
				try {
					quartzOperator.addJob(IdentityMantain.class, CronScheduleBuilder.cronSchedule(cronTask.getTaskCron()),
							null, data, cronTask.getStartDate(), cronTask.getEndDate());
				} catch (SchedulerException e) {
					logger.error("quartz scheduleJob 异常", e);
				} catch (ParseException e) {
					logger.error("job cron格式不正常，转换异常，task_id:" + cronTask.getTaskId() + ",cron:" + cronTask.getTaskCron(),
							e);
				}
			}

			try {
				quartzOperator.startQuartz();
			} catch (SchedulerException e) {
				logger.error("quartz startJob 异常", e);
			}
		}
		
	}
}
