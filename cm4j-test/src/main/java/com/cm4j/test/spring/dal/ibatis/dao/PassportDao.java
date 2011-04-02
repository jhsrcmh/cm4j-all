package com.cm4j.test.spring.dal.ibatis.dao;

import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.cm4j.test.spring.dal.ibatis.pojo.Passport;

@Service
public class PassportDao extends SqlSessionDaoSupport{
    private static final String NAMESPACE = "com.woniu.spring.dal.ibatis.dao.PassportDao.";
    private static final String STMT_QUERY_PASSPORT = NAMESPACE + "queryPassport";
    
    public Passport queryPassport(String userName) {
        return (Passport) getSqlSession().selectOne(STMT_QUERY_PASSPORT,StringUtils.upperCase(userName));
    }
}
