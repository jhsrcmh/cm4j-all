package com.cm4j.dao.datasource;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 决定连接到哪个数据源
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		Object obj = DataSourceHolder.getDataSourceType();
		logger.warn("当前动态数据源标识：" + obj);
		return obj;
	}

	/**
	 * 获取数据库连接信息
	 */
	@Override
	public void afterPropertiesSet() {
		// 调用spring方法
		super.afterPropertiesSet();

		// 获取数据库连接信息
		if (logger.isDebugEnabled()) {
			try {
				DatabaseMetaData meta = super.getConnection().getMetaData();
				StringBuffer sb = new StringBuffer();
				sb.append("\n ================ 获取当前连接信息：==================").append("\n DriverName:")
						.append(meta.getDriverName()).append("\n URL:").append(meta.getURL()).append("\n UserName:")
						.append(meta.getUserName()).append("\n ================ 当前连接信息结束：==================");
				logger.debug(sb.toString());
			} catch (SQLException e) {
				logger.error("获取数据库连接信息失败：" + e);
			}
		}
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}
}
