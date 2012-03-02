package org.hibernate.dialect;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

public class H2DataSource implements FactoryBean<DataSource>, DisposableBean {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private String url, user, password;
	private JdbcConnectionPool connectionPool;

	@Override
	public DataSource getObject() throws Exception {
		this.connectionPool = JdbcConnectionPool.create(url, user, password);
		// Server server = Server.createTcpServer(new String[] { "-tcpPort",
		// "9101" });
		// server.start();
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
	public void destroy() {
		try {
			this.connectionPool.dispose();
		} catch (Exception e) {
			logger.error("线程池dispose()异常", e);
		}
	}
}
