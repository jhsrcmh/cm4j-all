package com.cm4j.dao.datasource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Set<String> datasourceKeys;

	/**
	 * 决定连接到哪个数据源
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		String dataSourceKey = DataSourceHolder.getDataSourceType();
		if (StringUtils.isNotBlank(dataSourceKey) && !datasourceKeys.contains(dataSourceKey)){
			throw new IllegalArgumentException("配置中不包含此数据源：" + dataSourceKey);
		}
		logger.warn("当前动态数据源标识：" + dataSourceKey);
		return dataSourceKey;
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
			Connection conn = null;
			try {
				conn = super.getConnection();
				DatabaseMetaData meta = conn.getMetaData();
				StringBuffer sb = new StringBuffer();
				sb
						.append(
								"\n ================ 获取当前默认连接信息：==================")
						.append("\n DriverName:")
						.append(meta.getDriverName())
						.append("\n URL:")
						.append(meta.getURL())
						.append("\n UserName:")
						.append(meta.getUserName())
						.append(
								"\n ================ 当前默认连接信息结束：==================");
				logger.debug(sb.toString());
			} catch (SQLException e) {
				logger.error("获取数据库连接信息失败：" + e);
			} finally{
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						logger.error("关闭数据库连接异常",e);
					}
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setTargetDataSources(Map targetDataSources) {
		this.datasourceKeys = targetDataSources.keySet();
		super.setTargetDataSources(targetDataSources);
	}
}
