package com.cm4j.taobao.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cm4j.dao.hibernate.HibernateDao;
import com.cm4j.taobao.pojo.AsyncTask;
import com.cm4j.taobao.pojo.AsyncTask.State;
import com.cm4j.taobao.pojo.AsyncTask.TaskSubType;
import com.cm4j.taobao.pojo.AsyncTask.TaskType;
import com.cm4j.taobao.pojo.UserInfo;

@Repository
public class AsyncTaskDao extends HibernateDao<AsyncTask, Long> {

	/**
	 * 获取所有需要定时执行的任务
	 * 
	 * @return [SyncTask,UserInfo]
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getCronTasks() {
		// todo 加上执行失败次数上限限制
		String hql = "from AsyncTask t,UserInfo u where t.relatedId = u.userId and u.state = ?"
				+ " and t.state = ? and t.taskType = ? and sysdate between t.startDate and t.endDate";
		List<Object[]> result = (List<Object[]>) findAllWithHql(hql, new Object[] { UserInfo.State.normal.name(),
				AsyncTask.State.wating_operate.name(), TaskType.cron.name() });
		return result;
	}

	/**
	 * 插入异步任务
	 * 
	 * @param taskType
	 * @param taskSubType
	 * @param userId
	 * @param cron
	 * @param startDate
	 * @param endDate
	 * @param taskData
	 */
	public AsyncTask addAsyncTask(TaskType taskType, TaskSubType taskSubType, Long userId, String cron, Date startDate,
			Date endDate, String taskData) {
		AsyncTask asyncTask = new AsyncTask();
		asyncTask.setTaskType(taskType.name());
		asyncTask.setTaskSubType(taskSubType.name());

		asyncTask.setRelatedId(userId);
		asyncTask.setTaskCron(cron);
		asyncTask.setStartDate(startDate);
		asyncTask.setEndDate(endDate);
		asyncTask.setState(State.wating_operate.name());
		asyncTask.setTaskData(taskData);

		Long id = save(asyncTask);
		asyncTask.setTaskId(id);
		return asyncTask;
	}

	/* ========================单个用户 定时任务dao======================= */

	/**
	 * 获取单个用户待执行的橱窗推荐定时任务
	 * 
	 * @param userId
	 * @return
	 */
	public List<AsyncTask> getShowCaseCronTasks(Long userId) {
		return findAllByProperty(new String[] { "relatedId", "taskType", "taskSubType", "state" },
				new Object[] { userId, TaskType.cron.name(), TaskSubType.separate_showcase.name(),
						AsyncTask.State.wating_operate.name() });
	}

	/**
	 * 单个用户定时任务个数
	 * 
	 * @param userId
	 * @return
	 */
	public int countCronTask(Long userId) {
		Map<String, Object> paramValues = new HashMap<String, Object>();
		paramValues.put("relatedId", userId);
		paramValues.put("taskType", TaskType.cron.name());
		return countByProperty(paramValues);
	}

	/**
	 * 获取单个用户定时任务
	 * 
	 * @param userId
	 * @return
	 */
	public List<AsyncTask> getCronTasks(long userId, int pageSize, int pageNo) {
		Map<String, Object> paramValues = new HashMap<String, Object>();
		paramValues.put("relatedId", userId);
		paramValues.put("taskType", TaskType.cron.name());
		return pageByProperty(paramValues, pageSize, pageNo, "taskId", false);
	}
}
