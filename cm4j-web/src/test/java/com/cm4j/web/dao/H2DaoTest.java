package com.cm4j.web.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cm4j.dao.hibernate.HibernateDao;
import com.cm4j.dao.jdbc.SessionDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class H2DaoTest {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private SessionDao sessionDao;

	@SuppressWarnings("rawtypes")
	@Resource
	private HibernateDao hibernateDao;

	@Test
	public void jdbcTest() {
		Object p = 1;
		List<Map<String, Object>> result = sessionDao.findAll("select * from dual where 1 = :id",
				Collections.singletonMap("id", p));
		logger.debug("h2 jdbc test:{}", result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void hibernateTest() {
		Object p = 1;
		Object result = hibernateDao.find("from dual where 1 = :id", Collections.singletonMap("id", p));

		logger.debug("h2 hibernate test:{}", result);
	}
}
