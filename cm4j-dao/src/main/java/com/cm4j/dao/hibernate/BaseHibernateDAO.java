package com.cm4j.dao.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;

/**
 * 基于Spring的HibernateTemplate模板进行数据库操作接口，继承于通用接口层BaseDao<br />
 * -----------------------------------------------------------<br /> <b><font
 * color="red">DAO实现类已通过Autowired方式注入SessionFactory</font></b><br /> <b><font
 * color
 * ="red">DAO实现类可通过this.getHibernateTemplate()获得HibernateTemplate对象</font></
 * b><br /> -----------------------------------------------------------<br />
 * 
 * @author YangHao
 * @since 2008-12-7 下午12:20:54
 * @contact <a href="mailto:hao.yh@qq.com">hao.yh@qq.com</a>
 * @copyright Woniu.com
 */
public interface BaseHibernateDAO<E, ID extends Serializable> {
	/*****************************************************************************
	 * ------------------------------使用HQL语句---------------------------- ---- *
	 ****************************************************************************/

	/**
	 * 多表查询
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	Object findWithHql(String hql, Object[] values);

	/**
	 * 根据对象ID来查询对象(get方法)
	 * 
	 * @return 如果找到对应的对象，则返回该对象。如果不能找到，则返回null
	 * @throws DataAccessException
	 */
	E findById(ID id) throws DataAccessException;

	/**
	 * 根据条件查询单个对象，默认返回查询结果列表的第一个，如果未查询到则返回null
	 * 
	 * @param queryString
	 *            查询条件，仅有一个参数，<参数格式 -- ? >
	 * @param value
	 *            查询参数值
	 * @return 默认返回查询条件列表的第一个，如果未查询到则返回null
	 * @throws DataAccessException
	 */
	E find(String queryString, Object value) throws DataAccessException;

	/**
	 * 根据条件查询单个对象，默认返回查询结果列表的第一个，如果未查询到则返回null
	 * 
	 * @param queryString
	 *            查询条件为参数数组，<参数格式 -- ? >
	 * @param values
	 *            查询参数数组值
	 * @return 默认返回查询条件列表的第一个，如果未查询到则返回null
	 * @throws DataAccessException
	 */
	E find(String queryString, Object[] values) throws DataAccessException;

	/**
	 * 根据条件查询单个对象，默认返回查询结果列表的第一个，如果未查询到则返回null
	 * 
	 * @param queryString
	 *            查询语句
	 * @param paramValues
	 *            键值对参数
	 * @return
	 */
	E find(String queryString, Map<String, Object> paramValues)
			throws DataAccessException;

	/**
	 * 根据条件查询单个对象，默认返回查询结果列表的第一个，如果未查询到则返回null
	 * 
	 * @param queryString
	 *            <参数格式 -- : >
	 * @param paramName
	 * @param value
	 * @return
	 * @throws DataAccessException
	 */
	E find(String queryString, String paramName, Object value)
			throws DataAccessException;

	/**
	 * 根据条件查询单个对象，默认返回查询结果列表的第一个，如果未查询到则返回null
	 * 
	 * @param queryString
	 *            <参数格式 -- : >
	 * @param paramNames
	 *            参数名数组
	 * @param values
	 *            参数值数组
	 * @return
	 * @throws DataAccessException
	 */
	E find(String queryString, String[] paramNames, Object[] values)
			throws DataAccessException;

	/**
	 * 根据对象pojo的属性值查询对象
	 * 
	 * @param property
	 *            对象pojo的属性名称
	 * @param value
	 *            pojo属性名对应的属性值
	 * @return
	 * @throws DataAccessException
	 */
	E findByProperty(String property, Object value) throws DataAccessException;

	/**
	 * 根据对象pojo的属性值查询对象
	 * 
	 * @param properties
	 *            对象pojo属性名
	 * @param values
	 *            pojo属性名对应的属性值
	 * @return
	 * @throws DataAccessException
	 */
	E findByProperty(String[] properties, Object[] values)
			throws DataAccessException;

	/**
	 * 根据对象pojo的属性值查询对象
	 * 
	 * @param paramValues
	 *            键值对POJO属性
	 * @return
	 * @throws DataAccessException
	 */
	E findByProperty(Map<String, Object> paramValues)
			throws DataAccessException;

	/**
	 * 根据对象pojo的属性值Like查询对象
	 * 
	 * @param property
	 * @param value
	 * @return 有则返回，无则返回null
	 * @throws DataAccessException
	 */
	E findByPropertyLike(String property, String value)
			throws DataAccessException;

	/**
	 * 多表查询
	 * 
	 * @param hql
	 * @param values
	 * @return
	 * @throws DataAccessException
	 */
	List<?> findAllWithHql(String hql, Object[] values)
			throws DataAccessException;

	/**
	 * 查询所有的指定对象
	 * 
	 * @return E
	 */
	List<E> findAll() throws DataAccessException;

	/**
	 * 使用HQL语句进行查询
	 * 
	 * @param queryString
	 *            查询语句
	 * @return 符合条件的对象集吿
	 */
	List<E> findAll(String queryString) throws DataAccessException;

	/**
	 * 使用带单个参数的HQL语句进行查询
	 * 
	 * @param queryString
	 * @param param
	 * @return
	 */
	List<E> findAll(String queryString, Object value)
			throws DataAccessException;

	/**
	 * 使用带参数的语句进行查询
	 * 
	 * @param queryString
	 *            <参数格式 -- ? >
	 * @param params
	 * @return
	 */
	List<E> findAll(String queryString, Object[] values)
			throws DataAccessException;

	/**
	 * 使用带参数的语句进行查询
	 * 
	 * @param queryString
	 *            <参数格式 -- : >
	 * @param paramName
	 * @param value
	 * @return
	 */
	List<E> findAll(String queryString, String paramName, Object value)
			throws DataAccessException;

	/**
	 * 使用带参数的语句进行查询
	 * 
	 * @param queryString
	 *            <参数格式 -- : >
	 * @param paramNames
	 * @param values
	 * @return
	 * @throws DataAccessException
	 */
	List<E> findAll(String queryString, String[] paramNames, Object[] values)
			throws DataAccessException;

	/**
	 * 根据对象pojo的属性值查询对象
	 * 
	 * @param property
	 *            对象pojo的属性名称
	 * @param value
	 *            pojo属性名对应的属性值
	 * @return
	 * @throws DataAccessException
	 */
	List<E> findAllByProperty(String property, Object value)
			throws DataAccessException;

	/**
	 * 根据对象pojo的属性值查询对象，支持排序
	 * 
	 * @param property
	 *            对象pojo的属性名称
	 * @param value
	 *            pojo属性名对应的属性值
	 * @param orderBy
	 *            排序pojo的属性名称
	 * @param isAsc
	 *            是否为升序
	 * @return
	 * @throws DataAccessException
	 */
	List<E> findAllByProperty(String property, Object value, String orderBy,
			boolean isAsc) throws DataAccessException;

	/**
	 * 根据对象pojo的属性值查询对象
	 * 
	 * @param properties
	 *            对象pojo属性名
	 * @param values
	 *            pojo属性名对应的属性值
	 * @return
	 * @throws DataAccessException
	 */
	List<E> findAllByProperty(String[] properties, Object[] values)
			throws DataAccessException;

	/**
	 * 根据对象pojo的属性值查询对象
	 * 
	 * @param paramValues
	 *            键值对属性
	 * @return
	 * @throws DataAccessException
	 */
	List<E> findAllByProperty(Map<String, Object> paramValues)
			throws DataAccessException;

	/**
	 * 根据对象pojo的属性值Like查询对象
	 * 
	 * @param property
	 *            对象pojo属性名
	 * @param value
	 *            Like值
	 * @return 有则返回，无则返回null
	 */
	List<E> findAllByPropertyLike(String property, String value)
			throws DataAccessException;

	/**
	 * * @param propertyValues 键值对属性，<b>可以为null</b>
	 * 
	 * @param queryString
	 *            其他查询语句，如：param >= :param
	 * @param otherValues
	 *            其他查询语句的键值对参数
	 * @return
	 * @throws HibernateException
	 */
	List<E> findAllByPropertyAndOthers(Map<String, Object> propertyValues,
			String queryString, Map<String, Object> otherValues)
			throws HibernateException;

	/**
	 * 根据对象pojo的属性值查询对象，支持排序，支持分页
	 * 
	 * @param property
	 *            对象pojo的属性名称
	 * @param value
	 *            pojo属性名对应的属性值
	 * @param firstResult
	 *            每页开始记录索引值，第一个为0
	 * @param maxResults
	 *            每页记录个数
	 * @param orderBy
	 *            排序pojo属性，允许为null，<b>（默认）第二排序：ID降序</b>
	 * @param isAsc
	 *            若orderBy为null，则该属性失效
	 * @return
	 * @throws DataAccessException
	 */
	List<E> pageByProperty(String property, Object value, int firstResult,
			int maxResults, String orderBy, boolean isAsc)
			throws DataAccessException;

	/**
	 * 根据对象pojo的属性值查询对象，支持排序，支持分页
	 * 
	 * @param paramValues
	 *            键值对属性
	 * @param firstResult
	 *            每页开始记录索引值，第一个为0
	 * @param maxResults
	 *            每页记录个数
	 * @param orderBy
	 *            排序pojo属性，允许为null，<b>（默认）第二排序：ID降序</b>
	 * @param isAsc
	 *            若orderBy为null，则该属性失效
	 * @return
	 * @throws DataAccessException
	 */
	List<E> pageByProperty(Map<String, Object> paramValues, int firstResult,
			int maxResults, String orderBy, Boolean isAsc)
			throws DataAccessException;

	/**
	 * 根据对象pojo的属性值以及其他条件查询对象，支持排序，支持分页
	 * 
	 * @param propertyValues
	 *            键值对属性，<b>可以为null</b>
	 * @param queryString
	 *            其他查询语句，如：param >= :param
	 * @param otherValues
	 *            其他查询语句的键值对参数
	 * @param firstResult
	 *            每页开始记录索引值，第一个为0
	 * @param maxResults
	 *            每页记录个数
	 * @param orderBy
	 *            排序pojo属性，允许为null，<b>（默认）第二排序：ID降序</b>
	 * @param isAsc
	 *            若orderBy为null，则该属性失效
	 * @return
	 */
	List<E> pageByPropertyAndOthers(Map<String, Object> propertyValues,
			String queryString, Map<String, Object> otherValues,
			int firstResult, int maxResults, String orderBy, Boolean isAsc)
			throws DataAccessException;

	/**
	 * 根据对象pojo的属性值查询记录个数，没有返回0
	 * 
	 * @param paramValues
	 * @return
	 * @throws DataAccessException
	 */
	int countByProperty(Map<String, Object> paramValues)
			throws DataAccessException;

	/**
	 * 根据对象pojo的属性值以及其他条件查询符合条件记录数
	 * 
	 * @param propertyValues
	 *            键值对属性，<b>可以为null</b>
	 * @param queryString
	 *            其他查询语句，如：param >= :param
	 * @param otherValues
	 *            其他查询语句的键值对参数
	 * @return
	 */
	int countByPropertyAndOthers(Map<String, Object> propertyValues,
			String queryString, Map<String, Object> otherValues)
			throws DataAccessException;

	/**
	 * 使用查询语句检索数据，返回 Iterator
	 * 
	 * @param queryString
	 * @return Iterator<E>
	 */
	Iterator<E> iterate(String queryString) throws DataAccessException;

	/**
	 * 使用带单个参数的查询语句检索数据，返回 Iterator
	 * 
	 * @param queryString
	 * @param values
	 * @return Iterator<E>
	 */
	Iterator<E> iterate(String queryString, Object value)
			throws DataAccessException;

	/**
	 * 使用带参数的查询语句检索数据，返回 Iterator
	 * 
	 * @param queryString
	 * @param values
	 * @return Iterator<E>
	 */
	Iterator<E> iterate(String queryString, Object[] values)
			throws DataAccessException;

	/**
	 * <b>前提：必须手工打开统计选项</b><br /> hibernate.generate_statistics -- true<br />
	 * hibernate.cache.use_structured_entries -- true<br />
	 * 
	 * @param regionName
	 *            指定第二级缓存的区域名 -- 类或者集合的名字
	 * @return
	 */
	Map<String, Object> findSecondLevelCacheEntities(String regionName);

	///*************************************************************************
	// **
	// * -----------------------------保存、删除对象----------------------------- *
	//**************************************************************************
	// /

	/**
	 * 保存单个实体对象
	 * 
	 * @param entity
	 * @return ID 返回更新后实体对象ID
	 * @throws DataAccessException
	 */
	ID save(E entity) throws DataAccessException;

	/**
	 * 更新单个实体对象
	 * 
	 * @param entity
	 * @throws DataAccessException
	 */
	void update(E entity) throws DataAccessException;

	/**
	 * 根据条件批量更新对象
	 * 
	 * @param queryString
	 *            查询语句
	 * @return 更新的记录数
	 * @throws DataAccessException
	 */
	int update(String queryString) throws DataAccessException;

	/**
	 * 根据单个条件批量更新对象
	 * 
	 * @param queryString
	 * @param value
	 *            <参数格式 -- ? >
	 * @return 更新的记录数
	 * @throws DataAccessException
	 */
	int update(String queryString, Object value) throws DataAccessException;

	/**
	 * 根据条件批量更新对象
	 * 
	 * @param queryString
	 * @param vlaues
	 *            <参数格式 -- ? >
	 * @return
	 * @throws DataAccessException
	 */
	int update(String queryString, Object[] vlaues) throws DataAccessException;

	/**
	 * 增加或更新集合中的单个实体
	 * 
	 * @param entity
	 */
	void saveOrUpdate(E entity) throws DataAccessException;

	/**
	 * 增加或更新集合中的全部实体
	 * 
	 * @param entities
	 */
	void saveOrUpdateAll(Collection<E> entities) throws DataAccessException;

	/**
	 * 删除单个实体
	 * 
	 * @param entity
	 */
	void delete(E entity) throws DataAccessException;

	/**
	 * 根据主键删除指定实体
	 * 
	 * @param id
	 */
	void deleteById(ID id) throws DataAccessException;

	/**
	 * 批量删除
	 * 
	 * @param entities
	 */
	void deleteAll(Collection<E> entities) throws DataAccessException;

}
