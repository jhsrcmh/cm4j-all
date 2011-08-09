package com.cm4j.taobao.dao;

import java.io.FileNotFoundException;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.Test;
import org.springframework.dao.DataAccessException;

import com.cm4j.dao.utils.DBUtil;
import com.cm4j.taobao.api.common.APIConstants;

public class DbSchemaInit {

	@Test
	public void testLoadDBSchema() throws DataAccessException, FileNotFoundException {
		DataSource dataSource = JdbcConnectionPool.create(APIConstants.getH2Info("url"),
				APIConstants.getH2Info("user"), APIConstants.getH2Info("password"));
		new DBUtil().loadDBSchema(dataSource, "sql/schema.sql");
	}
}
