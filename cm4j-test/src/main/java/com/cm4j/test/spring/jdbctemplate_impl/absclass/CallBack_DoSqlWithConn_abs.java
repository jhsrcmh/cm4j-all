package com.cm4j.test.spring.jdbctemplate_impl.absclass;

import com.cm4j.test.spring.jdbctemplate_impl.Connection;

public abstract class CallBack_DoSqlWithConn_abs {

    public abstract Object doSql(Connection conn, String sql);
}
