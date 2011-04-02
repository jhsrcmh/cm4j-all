package com.cm4j.test.spring.jdbctemplate_impl.callback;

import com.cm4j.test.spring.jdbctemplate_impl.Connection;

public interface CallBack_DoSqlWithConn {

    public Object doSql (Connection conn);
}