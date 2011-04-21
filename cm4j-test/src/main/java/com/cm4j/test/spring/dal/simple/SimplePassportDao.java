package com.cm4j.test.spring.dal.simple;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.cm4j.test.spring.dal.ibatis.pojo.Passport;

public class SimplePassportDao extends JdbcDaoSupport {

    private static final String QUERY_PASSPORT = "select S_ACCOUNT as userName, S_PASSWD as pwd from sn_passport.pp_passport where S_ACCOUNT = ?";

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() {
        super.setDataSource(dataSource);
    }

    private RowMapper<Passport> passportWrap = new RowMapper<Passport>() {
        @Override
        public Passport mapRow(ResultSet rs, int rowNum) throws SQLException {
            Passport passport = new Passport();
            passport.setUserName(rs.getString(1));
            passport.setPwd(rs.getString(2));
            return passport;
        }
    };

    public Passport queryPassportByName(String userName) throws DataAccessException {
        return getJdbcTemplate().queryForObject(QUERY_PASSPORT, passportWrap, StringUtils.upperCase(userName));
    }
}
