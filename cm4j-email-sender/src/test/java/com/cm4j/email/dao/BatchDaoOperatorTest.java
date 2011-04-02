package com.cm4j.email.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-email.xml" })
public class BatchDaoOperatorTest {

	@Autowired
	private BatchDaoOperator batchDao;

	@Test
	public void testBatchInsertOutbox() {
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		Object[] obj = new Object[] { "b@s.com", "userName2", "pwd", "hostname", 21 };
		batchArgs.add(obj);
		batchDao.batchInsertEmailOutbox(batchArgs);
	}

	@Test
	public void testBatchInsertInbox() {
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		Object[] obj = new Object[] { "a@a.com" };
		batchArgs.add(obj);
		batchDao.batchInsertEmailInbox(batchArgs);
	}

}
