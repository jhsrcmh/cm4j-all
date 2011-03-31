package com.cm4j.test.spring.jdbctemplate_impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionUtil.class);

    /**
     * 创建conn
     * 
     * @return
     */
    public static Connection createConnection() {
        Connection conn = new Connection() {
        };
        logger.debug("create conn:{}", conn);
        return conn; 
    }

    /**
     * 销毁conn
     * 
     * @param conn
     */
    public static void destroyConnection(Connection conn) {
        logger.debug("destroy conn:{}", conn);
    }
}
