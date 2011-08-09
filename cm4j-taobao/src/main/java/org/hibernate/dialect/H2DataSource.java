package org.hibernate.dialect;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcConnectionPool;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

public class H2DataSource implements FactoryBean<DataSource>, DisposableBean {

	private String url, user, password;

	private JdbcConnectionPool connectionPool;

	@Override
	public DataSource getObject() throws Exception {
		this.connectionPool = JdbcConnectionPool.create(url, user, password);
		return connectionPool;
	}

	@Override
	public Class<?> getObjectType() {
		return DataSource.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void destroy() throws Exception {
		this.connectionPool.dispose();
	}

}
