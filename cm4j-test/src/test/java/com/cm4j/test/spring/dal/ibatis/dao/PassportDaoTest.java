package com.cm4j.test.spring.dal.ibatis.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cm4j.test.spring.dal.ibatis.pojo.Passport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class PassportDaoTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PassportDao passportDao;

    @Test
    public void testQueryPassport() {
        Passport pp = passportDao.queryPassport("test2001");
        logger.debug("pp.userName:{},pp.pwd:{}", pp.getUserName(), pp.getPwd());
    }
}
