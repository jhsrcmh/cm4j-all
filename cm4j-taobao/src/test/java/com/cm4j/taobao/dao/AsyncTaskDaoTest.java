package com.cm4j.taobao.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cm4j.dao.hibernate.HibernateDao;
import com.cm4j.taobao.pojo.AsyncTask;
import com.cm4j.taobao.pojo.AsyncTask.TaskSubType;
import com.cm4j.taobao.pojo.AsyncTask.TaskType;
import com.cm4j.taobao.pojo.UserInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class AsyncTaskDaoTest {

	@Autowired
	private AsyncTaskDao asyncTaskDao;
	@Resource
	private HibernateDao<UserInfo, Long> userInfoDao;

	public Long testAdd() {
		AsyncTask cronTask = new AsyncTask();
		cronTask.setTaskType(TaskType.cron.name());
		cronTask.setTaskSubType(TaskSubType.identity_mantain.name());
		cronTask.setRelatedId(55496072L);
		cronTask.setTaskCron("0 0/1 * * * ?");
		cronTask.setTaskData("");
		cronTask.setStartDate(AsyncTask.DATE_NOW.apply());
		cronTask.setEndDate(AsyncTask.DATE_FOREVER.apply());
		cronTask.setState(AsyncTask.State.wating_operate.name());

		return asyncTaskDao.save(cronTask);
	}

	@Test
	public void testGetCronTasks() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(55496072L);
		userInfo.setUserNick("syniiii");
		userInfo.setState(UserInfo.State.normal.name());
		userInfo.setVersionNo(1);
		userInfo.setUpdateDate(new Date());
		userInfo.setSessionKey("50810117fe140343effTQTfsRj5b39dc0b779f038761bf5554960722");
		userInfoDao.saveOrUpdate(userInfo);

		Long taskId = testAdd();

		List<Object[]> result = asyncTaskDao.getCronTasks();
		Assert.assertNotNull(result);

		asyncTaskDao.deleteById(taskId);
	}
}
