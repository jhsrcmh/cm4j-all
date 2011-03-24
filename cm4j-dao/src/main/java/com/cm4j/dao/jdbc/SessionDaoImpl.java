package com.cm4j.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cm4j.dao.exception.Cm4jDataAccessException;

/**
 * <p>
 * 基于Spring的SimpleJdbcTemplate模板的具体数据库操作实现
 * <p>
 * -----------------------------------------------------------
 * <p>
 * <b><font color="red">DAO实现类已通过Autowired方式注入{@link DataSource}</font></b>
 * <p>
 * <b><font color="red">DAO实现类可通过get***Template()获得各个Template对象</font></b>
 * <p>
 * -----------------------------------------------------------
 * 
 * @author YangHao
 * @since 2009-1-12 下午03:17:04
 * @Contact <a href="mailto:hao.yh@qq.com">hao.yh@qq.com</a>
 * @copyright Woniu.com
 */
@Repository
public class SessionDaoImpl implements BaseSessionDAO {

	// log4j日志
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	protected JdbcTemplate jdbcTemplate;
	protected SimpleJdbcTemplate simpleJdbcTemplate;
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	// ***************************************************************************
	// * ------------------------------具体实现方法------------------------------ *
	// **************************************************************************/

	/**
	 * 返回结果为List，每个List包括键值对Map，Key为Sql查询语句的列名
	 */
	public List<Map<String, Object>> findAll(String queryString, Map<String, Object> paramValues)
			throws DataAccessException {
		List<Map<String, Object>> result = this.namedParameterJdbcTemplate.queryForList(queryString, paramValues);
		logger.debug("传入sql：" + queryString);
		logger.debug("传入键值对参数：" + paramValues);
		return result;
	}

	public List<?> page(String queryString, Map<String, Object> paramValues, int firstRowNum, int pageRowCount)
			throws DataAccessException {
		if (MapUtils.isEmpty(paramValues)) {
			throw new Cm4jDataAccessException("查询参数不允许为空");
		}
		StringBuilder sqlBuilder = new StringBuilder("select * from (select row_.*,rownum rownum_ from (").append(
				queryString).append(") row_ where rownum <= :maxRowNum) where rownum_> :firstRowNum");
		paramValues.put("firstRowNum", firstRowNum);
		paramValues.put("maxRowNum", firstRowNum + pageRowCount);
		logger.debug("传入sql：" + sqlBuilder.toString());
		logger.debug("传入键值对参数：" + paramValues);
		return this.simpleJdbcTemplate.queryForList(sqlBuilder.toString(), paramValues);
	}

	public List<?> page(String queryString, Map<String, Object> paramValues, int firstRowNum, int pageRowCount,
			String orderBy, boolean isAsc) throws DataAccessException {
		if (MapUtils.isEmpty(paramValues)) {
			throw new Cm4jDataAccessException("查询参数不允许为空");
		}
		StringBuilder sqlBuilder = new StringBuilder("select * from (select row_.*,rownum rownum_ from (").append(
				queryString).append(") row_ where rownum <= :maxRowNum) where rownum_> :firstRowNum");
		sqlBuilder.append(" ORDER BY ").append(orderBy).append(isAsc ? " ASC" : " DESC");
		paramValues.put("firstRowNum", firstRowNum);
		paramValues.put("maxRowNum", firstRowNum + pageRowCount);

		logger.debug("传入sql：" + sqlBuilder.toString());
		logger.debug("传入键值对参数：" + paramValues);
		return this.simpleJdbcTemplate.queryForList(sqlBuilder.toString(), paramValues);
	}

	public int count(String queryString, Map<String, Object> paramValues) throws DataAccessException {
		int result = 0;
		try {
			result = this.namedParameterJdbcTemplate.queryForInt(queryString, paramValues);
		} catch (IncorrectResultSizeDataAccessException e) {
			logger.debug("查询记录数为0");
		}
		return result;
	}

	public List<Map<String, Object>> executeWithResult(String spName, Map<String, Object> parameters,
			Map<String, Integer> outParams, String cursorName) throws DataAccessException {

		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		if (spName == null) {
			throw new Cm4jDataAccessException("存储过程名称不能为空");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("调用存储过程: " + spName);
			logger.debug("过程输入参数=" + parameters);
			logger.debug("过程输出参数=" + outParams);
		}

		CallableStatement cmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
		} catch (SQLException e) {
			throw new Cm4jDataAccessException("打开数据库连接异常", e);
		}
		int pCount = ((parameters == null) ? 0 : parameters.size()) + ((outParams == null) ? 0 : outParams.size());
		StringBuilder sb = new StringBuilder(40);

		sb.append("{call ").append(spName).append("(");
		for (int i = 0; i < pCount; i++) {
			if (i != 0)
				sb.append(",");
			sb.append("?");
		}
		sb.append(")}");

		String sql = sb.toString();
		Map<String, Object> ret = new TreeMap<String, Object>();
		try {
			cmt = conn.prepareCall(sql);
			if (parameters != null && parameters.size() > 0) {
				Set<String> keys = parameters.keySet();
				for (String key : keys) {
					Object value = parameters.get(key);
					if (value instanceof java.util.Date) {
						cmt.setDate(key, new Date(((java.util.Date) value).getTime()));
					} else {
						cmt.setObject(key, value);
					}

				}
			}
			if (outParams != null && outParams.size() > 0) {
				Set<String> keys = outParams.keySet();
				for (String key : keys) {
					Integer value = outParams.get(key);
					cmt.registerOutParameter(key, value.intValue());
				}
			}
			try {
				cmt.execute();
			} catch (RuntimeException e) {
				logger.error(e.getMessage() + e.getStackTrace());
				return null;
			}
			int resultCode = cmt.getInt("piResult");
			if (resultCode != 1) {
				ret.put("piResult", resultCode);
				if (outParams.get("psErrDesc") != null) {
					ret.put("psErrDesc", cmt.getString("psErrDesc"));
					logger.error("调用过程" + spName + "报错，psErrDesc:" + cmt.getString("psErrDesc"));
					throw new Cm4jDataAccessException("调用过程" + spName + "报错，psErrDesc:" + cmt.getString("psErrDesc"));
				}
			}
			if (outParams != null && outParams.size() > 0) {
				Set<String> keys = outParams.keySet();
				for (String key : keys) {
					if (key.equals(cursorName)) {
						continue;
					}
					Object value = cmt.getObject(key);
					if (value != null) {
						ret.put(key, value);
					}
				}
			}
			// 将其他返回信息放入结果
			data.add(ret);

			// 将游标中数据放入结果
			if (cursorName != null) {
				try {
					rs = (ResultSet) cmt.getObject(cursorName);
				} catch (SQLException e) {
					logger.warn("存储过程没有游标可以打开");
					return data;
				}
				if (rs != null) {
					while (rs.next()) {
						Map<String, Object> row = new LinkedHashMap<String, Object>();
						int n = rs.getMetaData().getColumnCount();
						for (int i = 0; i < n; i++) {
							row.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i + 1));
						}
						data.add(row);
					}
				}
			}
		} catch (Throwable e) {
			throw new DataRetrievalFailureException("ERROR", e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (cmt != null) {
					cmt.close();
					cmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();// 关闭异常
			}
		}
		return data;
	}

	@Override
	public Long findForLong(String sql) throws DataAccessException {
		return jdbcTemplate.queryForLong(sql);
	}

	// ***************************************************************************
	// * ------------------------------相关注入方法------------------------------ *
	// **************************************************************************/
	/**
	 * Setter注入datasource，同时获得Template
	 * 
	 * @param dataSource
	 */
	@Resource
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}

	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

}