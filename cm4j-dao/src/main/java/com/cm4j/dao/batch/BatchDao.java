package com.cm4j.dao.batch;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public class BatchDao {

    protected SimpleJdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void batchUpdate(String sql, List<Object[]> batchArgs) {
        try {
            jdbcTemplate.batchUpdate(sql, batchArgs);
        } catch (Exception e) {
            // 其他异常
            for (Object[] arg : batchArgs) {
                try {
                    jdbcTemplate.update(sql, arg);
                } catch (DuplicateKeyException e1) {
                    logger.warn("重复插入,params:{}", arg);
                } catch (Exception e2) {
                    logger.error("插入失败,params:{}" + arg, e2);
                }
            }
        }
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

}
