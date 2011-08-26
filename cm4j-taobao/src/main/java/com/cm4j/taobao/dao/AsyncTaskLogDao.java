package com.cm4j.taobao.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cm4j.dao.hibernate.HibernateDao;
import com.cm4j.taobao.pojo.AsyncTaskLog;

@Repository
public class AsyncTaskLogDao extends HibernateDao<AsyncTaskLog, Long> {

	/**
	 * 查询任务taskId的执行次数
	 * 
	 * @param taskId
	 * @return
	 */
	public int countByTaskId(long taskId) {
		Object task_Id = taskId;
		return countByProperty(Collections.singletonMap("taskId", task_Id));
	}

	/**
	 * 分页查询日志记录
	 * 
	 * @param taskId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<AsyncTaskLog> getAsyncTaskLogs(long taskId, int pageSize, int pageNo) {
		Map<String, Object> paramValues = new HashMap<String, Object>();
		paramValues.put("taskId", taskId);
		return pageByProperty(paramValues, pageSize, pageNo, "logId", false);
	}
}
