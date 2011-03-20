package com.cm4j.dao.utils;

import java.io.FileNotFoundException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cm4j.dao.utils.DBUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-dao.xml" })
public class DBUtilTest {

    @Autowired
    private DataSource dataSource;

    DBUtil dbUtil = new DBUtil();

    @Test
    public void testLoadDBSchema() throws DataAccessException, FileNotFoundException {
        dbUtil.loadDBSchema(dataSource, "sqlmap/schema.sql");
    }
}
