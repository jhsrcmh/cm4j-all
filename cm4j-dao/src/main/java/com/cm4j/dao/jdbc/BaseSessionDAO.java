package com.cm4j.dao.jdbc;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * 提供基于Session的业务数据操作接口，功能比较全面<br /> 所有CRUD(创建，读取，修改和删除)基本数据的操作在这个接口中，都是独立的<br /> 所有的DAO都可以使用这些基本操作<br />
 * -----------------------------------------------------------<br /> <b><font
 * color="red">DAO实现类需要注入DataSource</font></b><br /> -----------------------------------------------------------<br />
 * 
 * @author YangHao
 * @since 2008-12-6 下午07:52:14
 * @contact <a href="mailto:hao.yh@qq.com">mailto:hao.yh@qq.com</a>
 * @copyright Woniu.com
 */
public interface BaseSessionDAO  {

	/**
	 * 根据条件分页查询记录，<b>（默认）第二排序：ID降序</b>
	 * 
	 * @param queryString
	 * @param paramValues
	 *            <参数格式 -- : >
	 * @param firstRowNum
	 *            每页开始记录索引值，第一个为0
	 * @param pageRowCount
	 *            每页记录个数
	 * @param orderBy
	 *            排序pojo属性，允许为null，<b>（默认）第二排序：ID降序</b>
	 * @param isAsc
	 *            若orderBy为null，则该属性失效
	 * @return 若采用sql方式查询，发挥list，每一行为一个键值对Map，键为sql语句中查询结果（和数据库一致）
	 * @throws DataAccessException
	 */
	List<?> page(String queryString, Map<String, Object> paramValues, int firstRowNum, int pageRowCount, String orderBy,
			boolean isAsc) throws DataAccessException;

	/**
	 * 查询sql 返回Long
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 */
	Long findForLong (String sql) throws DataAccessException;
	
	public SimpleJdbcTemplate getSimpleJdbcTemplate();

	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate();

	public JdbcTemplate getJdbcTemplate() ;
}
