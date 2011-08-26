package com.cm4j.taobao.service.async.quartz;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.cm4j.taobao.dao.AsyncTaskDao;
import com.cm4j.taobao.pojo.AsyncTask;
import com.cm4j.taobao.pojo.AsyncTask.TaskSubType;
import com.cm4j.taobao.pojo.UserInfo;
import com.cm4j.taobao.service.async.quartz.data.QuartzJobData;

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
	private AsyncTaskDao asyncTaskDao;
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

	class StartJobsRunnable implements Runnable {
		@Override
		public void run() {
			// todo 任务太多 分页？
			List<Object[]> results = asyncTaskDao.getCronTasks();
			if (CollectionUtils.isEmpty(results)) {
				logger.warn("未查询到可执行的定时任务");
				return;
			}
			for (Object[] result : results) {
				AsyncTask asyncTask = (AsyncTask) result[0];
				UserInfo userInfo = (UserInfo) result[1];

				addCronTask(asyncTask, userInfo.getSessionKey());
			}

			try {
				quartzOperator.startQuartz();
			} catch (SchedulerException e) {
				logger.error("quartz startJob 异常", e);
			}
		}
	}

	/**
	 * 添加定时任务到quartz
	 * 
	 * @param asyncTask
	 * @param sessionKey
	 */
	public void addCronTask(AsyncTask asyncTask, String sessionKey) {
		QuartzJobData data = new QuartzJobData();
		data.setTaskId(asyncTask.getTaskId());
		data.setUserId(asyncTask.getRelatedId());
		data.setSessionKey(sessionKey);
		data.setCron(asyncTask.getTaskCron());
		data.setJsonData(asyncTask.getTaskData());
		data.setStartDate(asyncTask.getStartDate());
		data.setEndDate(asyncTask.getEndDate());
		try {
			data.setHandlerClazz(TaskSubType.valueOf(asyncTask.getTaskSubType()).getHandleClazz());
			quartzOperator.addJob(data);
		} catch (SchedulerException e) {
			logger.error("quartz scheduleJob 异常", e);
		} catch (ParseException e) {
			logger.error("job cron格式不正常，转换异常，task_id:" + asyncTask.getTaskId() + ",cron:" + asyncTask.getTaskCron(), e);
		} catch (Exception e) {
			logger.error("添加job异常，task_id:" + asyncTask.getTaskId() + ",cron:" + asyncTask.getTaskCron(), e);
		}
	}
}
