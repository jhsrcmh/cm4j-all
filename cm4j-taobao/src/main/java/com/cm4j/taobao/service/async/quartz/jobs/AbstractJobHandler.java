package com.cm4j.taobao.service.async.quartz.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;

import com.cm4j.taobao.dao.AsyncTaskLogDao;
import com.cm4j.taobao.pojo.AsyncTask.DATE_ENUM;
import com.cm4j.taobao.pojo.AsyncTaskLog;
import com.cm4j.taobao.service.async.quartz.QuartzOperator;
import com.cm4j.taobao.service.async.quartz.data.QuartzJobData;

/**
 * Job处理器
 * 
 * @author yang.hao
 * @since 2011-8-23 下午6:35:54
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public abstract class AbstractJobHandler implements Job {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void execute(JobExecutionContext context) {
		QuartzJobData data = (QuartzJobData) context.getJobDetail().getJobDataMap()
				.get(QuartzOperator.JOBDETAIL_DATA_KEY);
		ApplicationContext ctx = (ApplicationContext) context.getJobDetail().getJobDataMap()
				.get(QuartzOperator.APPLICATION_CONTEXT);

		boolean is_success = false;
		String exec_info = "";
		try {
			exec_info = handle(data, ctx);
			is_success = true;
		} catch (Exception e) {
			logger.error("cronTask exec error，jobKey：" + context.getJobDetail().getKey().toString(), e);
			exec_info = e.getMessage();
			handleException(e);
		}

		try {
			// 插入执行记录
			AsyncTaskLog log = new AsyncTaskLog();
			log.setTaskId(data.getTaskId());
			log.setState(is_success ? AsyncTaskLog.State.success.name() : AsyncTaskLog.State.failed.name());
			log.setExecInfo(exec_info);
			log.setExecDate(DATE_ENUM.NOW.apply());

			AsyncTaskLogDao asyncTaskLogDao = ctx.getBean(AsyncTaskLogDao.class);
			asyncTaskLogDao.save(log);
		} catch (DataAccessException e) {
			logger.error("cron task exec log error", e);
		}
	}

	/**
	 * 执行Job
	 * 
	 * @param quartzJobData
	 * @param ctx
	 * @return 执行相关记录，存放在async_task_log.exec_info，如果执行异常，存放异常信息，没有则返回null
	 * @throws Exception
	 */
	protected abstract String handle(QuartzJobData quartzJobData, ApplicationContext ctx) throws Exception;

	/**
	 * 异常处理 - 可不做处理
	 * 
	 * @param e
	 */
	protected abstract void handleException(Exception e);

}
