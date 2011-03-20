package com.cm4j.email.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cm4j.email.pojo.Software;

@Repository
public class SoftwareDao extends MyBatisDao {

    private static final String STMT_QUERY_SOFTWARE_BY_NAME = MyBatisDao.EMAIL_NAMESPACE + "querySoftware";

    private static final String STMT_INSERT_SOFTWARE = MyBatisDao.EMAIL_NAMESPACE + "insertSoftware";

    public Software querySoftware(String softwareName) throws DataAccessException {
        return (Software) getSqlSession().selectOne(STMT_QUERY_SOFTWARE_BY_NAME, softwareName);
    }

    public int insertSoftware(Software software) throws DataAccessException {
        return getSqlSession().insert(STMT_INSERT_SOFTWARE, software);
    }

}