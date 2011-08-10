package com.cm4j.taobao.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cm4j.dao.hibernate.HibernateDao;
import com.cm4j.taobao.pojo.CronTask;
import com.cm4j.taobao.pojo.UserInfo;

@Repository
public class CronTaskDao extends HibernateDao<CronTask, Long> {

	/**
	 * 获取所有需要定时执行的任务
	 * 
	 * @return [SyncTask,UserInfo]
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getCronTasks() {
		String hql = "from CronTask t,UserInfo u where t.userId = u.userId and u.state = ?"
				+ " and t.state = ? and sysdate between t.startDate and t.endDate";
		List<Object[]> result = (List<Object[]>) findAllWithHql(hql, new Object[] { UserInfo.STATE_NORMAL, CronTask.STATE_VALID });
		return result;
	}
}
