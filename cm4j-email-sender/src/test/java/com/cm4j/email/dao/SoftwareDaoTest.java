package com.cm4j.email.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cm4j.email.pojo.Software;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-email.xml" })
public class SoftwareDaoTest {

	@Autowired
	private SoftwareDao softwareDao;

	@Test
	public void testQuerySoftWare() {
		Software software = null;
		try {
			// TODO 名字需修改
			software = softwareDao.querySoftware("A");
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(software);
	}

	@Test
	public void testInsertSoftWare() {
		Software software = new Software();
		software.setName("eMail_sender");
		software.setState("1");
		software.setVersion("1.0.0-SNAPSHOT");
		software.setCheckSum("checkSum");
		int result = 0;
		try {
			result = softwareDao.insertSoftware(software);
		} catch (DuplicateKeyException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(1, result);
	}
}
