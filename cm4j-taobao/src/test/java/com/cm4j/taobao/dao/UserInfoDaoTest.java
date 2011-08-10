package com.cm4j.taobao.dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cm4j.dao.hibernate.HibernateDao;
import com.cm4j.taobao.pojo.UserInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class UserInfoDaoTest {

	@Autowired
	private HibernateDao<UserInfo, Long> userInfoDao;
	
	@Test
	public void testSaveorupdate (){
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(180812L);
		userInfo.setUserNick("TEST2001");
		userInfo.setState("1");
		userInfo.setVersionNo(1);
		userInfo.setUpdateDate(new Date());
		userInfo.setLeaseId(1234L);
		userInfo.setSessionKey("lajslfjalfallajldsfjasdf");
		userInfoDao.saveOrUpdate(userInfo);
	}
}
