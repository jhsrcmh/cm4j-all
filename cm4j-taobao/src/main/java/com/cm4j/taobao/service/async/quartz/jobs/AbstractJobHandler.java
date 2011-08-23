package com.cm4j.taobao.service.async.quartz.jobs;

import javax.annotation.Resource;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.cm4j.dao.hibernate.HibernateDao;
import com.cm4j.taobao.pojo.AsyncTask;
import com.cm4j.taobao.pojo.AsyncTaskLog;
import com.cm4j.taobao.service.async.quartz.QuartzJobData;
import com.cm4j.taobao.service.async.quartz.QuartzOperator;

/**
 * Job处理器
 * 
 * @author yang.hao
 * @since 2011-8-23 下午6:35:54
 */
public abstract class AbstractJobHandler implements Job {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private HibernateDao<AsyncTaskLog, Long> asyncTaskLogDao;

	@Override
	public void execute(JobExecutionContext context) {
		QuartzJobData data = (QuartzJobData) context.getJobDetail().getJobDataMap()
				.get(QuartzOperator.JOBDETAIL_DATA_KEY);

		boolean is_success = false;
		String exec_info = "";
		try {
			handle(data);
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
			log.setState(is_success ? AsyncTaskLog.State.success.getState() : AsyncTaskLog.State.failed.getState());
			log.setExecInfo(exec_info);
			log.setExecDate(AsyncTask.DATE_NOW.apply());
			asyncTaskLogDao.save(log);
		} catch (DataAccessException e) {
			logger.error("cron task exec log error", e);
		}
	}

	/**
	 * 执行Job
	 * 
	 * @param context
	 * @throws Exception
	 */
	protected abstract void handle(QuartzJobData quartzJobData) throws Exception;
	
	/**
	 * 异常处理 - 可不做处理
	 * 
	 * @param e
	 */
	protected abstract void handleException(Exception e);

}
