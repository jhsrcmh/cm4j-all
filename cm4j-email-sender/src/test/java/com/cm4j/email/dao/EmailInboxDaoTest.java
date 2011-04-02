package com.cm4j.email.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cm4j.email.pojo.EmailInbox;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-email.xml" })
public class EmailInboxDaoTest {

	@Autowired
	private EmailInboxDao emailInboxDao;

	@Test
	public void testQueryInboxToSend() {
		// TODO 未查到
		List<EmailInbox> inboxList = emailInboxDao.queryInboxToSend(1);
		Object inbox = inboxList.get(0); // inbox 是 null
		Assert.assertNotNull(inboxList);
		Assert.assertEquals(1, inboxList.size());
	}
}
