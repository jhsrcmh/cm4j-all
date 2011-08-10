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
import com.cm4j.taobao.pojo.CronTask;
import com.cm4j.taobao.pojo.UserInfo;
import com.cm4j.taobao.service.async.quartz.QuartzTaskType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class CronTaskDaoTest {

	@Autowired
	private CronTaskDao cronTaskDao;
	@Resource
	private HibernateDao<UserInfo, Long> userInfoDao;

	public Long testAdd() {
		CronTask cronTask = new CronTask();
		cronTask.setTaskType(QuartzTaskType.identity_mantain.name());
		cronTask.setUserId(55496072L);
		cronTask.setTaskCron("0 0/20 * * * ?");
		cronTask.setStartDate(new Date());
		cronTask.setEndDate(CronTask.DATE_FOREVER);
		cronTask.setState(CronTask.STATE_VALID);

		return cronTaskDao.save(cronTask);
	}

	@Test
	public void testGetCronTasks() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(55496072L);
		userInfo.setUserNick("syniiii");
		userInfo.setState(UserInfo.STATE_NORMAL);
		userInfo.setVersionNo(1);
		userInfo.setUpdateDate(new Date());
		userInfo.setSessionKey("50810117fe140343effTQTfsRj5b39dc0b779f038761bf5554960722");
		userInfoDao.saveOrUpdate(userInfo);
		
		Long taskId = testAdd();

		List<Object[]> result = cronTaskDao.getCronTasks();
		Assert.assertNotNull(result);
		
		cronTaskDao.deleteById(taskId);
	}
}
