package com.cm4j.dao.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;

import com.cm4j.core.utils.IOUtils;

public class DBUtil {
	String contents = null;

	public void loadDBSchema(DataSource dataSource, String schemaName) throws DataAccessException,
			FileNotFoundException {
		JdbcTemplate template = new JdbcTemplate(dataSource);
		InputStream is = null;
		try {
			is = new ClassPathResource(schemaName).getInputStream();
			contents = IOUtils.toString(is);
		} catch (IOException e) {
			throw new FileNotFoundException("schema[" + schemaName + "]" + "is not found");
		}

		template.execute(new StatementCallback<Boolean>() {
			@Override
			public Boolean doInStatement(Statement stmt) throws SQLException, DataAccessException {
				return stmt.execute(contents);
			}
		});
	}
}
