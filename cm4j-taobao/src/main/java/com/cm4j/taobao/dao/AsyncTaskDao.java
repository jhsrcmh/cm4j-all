package com.cm4j.taobao.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cm4j.dao.hibernate.HibernateDao;
import com.cm4j.taobao.pojo.AsyncTask;
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
}
