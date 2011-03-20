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
     * @return
     * @throws DataAccessException
     */
    public EmailOutbox randomQueryOutbox() throws DataAccessException {
        return (EmailOutbox) getSqlSession().selectOne(STMT_RANDOM_QUERY_OUTBOX);
    }
}
