package com.cm4j.email.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cm4j.email.pojo.EmailOutbox;

@Repository
public class EmailOutboxDao extends MyBatisDao {

	private static final String STMT_RANDOM_QUERY_OUTBOX = MyBatisDao.EMAIL_NAMESPACE + "randomQueryOutbox";

	/**
	 * 随机获取一个可用的发件箱
	 * 
	 * 策略：a.先获取未验证的发件箱 - 发送后会修改发件箱状态 b.如果未找到，则从验证成功的发件箱中获取发送成功次数最少的邮箱
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public EmailOutbox randomQueryOutbox() throws DataAccessException {
		// TODO selectOne会抛异常
		return (EmailOutbox) getSqlSession().selectOne(STMT_RANDOM_QUERY_OUTBOX);
	}
	
	/**
	 * 获取一个等待校验的发件箱
	 * 
	 * @return
	 */
	public EmailOutbox queryToVerifyEmailOutbox() throws DataAccessException{
		
		return null;
	}
}
