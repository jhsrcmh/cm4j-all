package com.cm4j.test.spring.dal.batch;

import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 批处理插入
 * 可能会存在失败，因此需要做好插入或更新失败的后续处理
 * </pre>
 * 
 * @author yanghao
 * 
 */
@Service
public class BatchInsertUsage {

    private static Logger logger = LoggerFactory.getLogger(BatchInsertUsage.class);

    public static final String INSERT_PASSPORT = "INSERT INTO SN_PASSPORT.PP_PASSPORT(N_AID,S_ACCOUNT,S_PASSWD,S_VIP_LEVEL,S_EMAIL,N_IP,S_STATE,N_ISSUER_ID,D_CREATE) "
            + "VALUES(SN_PASSPORT.PP_PASSPORT_SQ.nextval,?,'E10ADC3949BA59ABBE56E057F20F883E','0','h@h.com',null,'1',7,sysdate)";

    private SimpleJdbcTemplate simpleJdbcTemplate;

    public void insertPassport(String sql, List<Object[]> userNames) {
        try {
            int[] result = simpleJdbcTemplate.batchUpdate(sql, userNames);
        } catch (Exception e) {
            // 其他异常
            for (Iterator<Object[]> iterator = userNames.iterator(); iterator.hasNext();) {
                Object[] params = iterator.next();
                try {
                    int i = simpleJdbcTemplate.update(sql, params);
                } catch (DuplicateKeyException e1) {
                    logger.error("重复插入,params:{}", params);
                } catch (Exception e2) {
                    logger.error("插入失败,params:{}", params);
                }
            }
        }

    }

    @Autowired
    public void setDataSource(DataSource ds) {
        simpleJdbcTemplate = new SimpleJdbcTemplate(ds);
    }

}
