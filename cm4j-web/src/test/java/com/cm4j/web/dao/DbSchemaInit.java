package com.cm4j.web.dao;

import java.io.FileNotFoundException;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.Test;
import org.springframework.dao.DataAccessException;

import com.cm4j.dao.utils.DBUtil;
import com.cm4j.web.utils.WebConstants;

public class DbSchemaInit {

	@Test
	public void testLoadDBSchema() throws DataAccessException, FileNotFoundException {
		DataSource dataSource = JdbcConnectionPool.create(WebConstants.getH2Info("url"),
				WebConstants.getH2Info("user"), WebConstants.getH2Info("password"));
		new DBUtil().loadDBSchema(dataSource, "sql/schema.sql");
	}
}
