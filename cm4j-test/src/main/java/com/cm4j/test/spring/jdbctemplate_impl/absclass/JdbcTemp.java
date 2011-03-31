package com.cm4j.test.spring.jdbctemplate_impl.absclass;

import com.cm4j.test.spring.jdbctemplate_impl.Connection;
import com.cm4j.test.spring.jdbctemplate_impl.ConnectionUtil;

/**
 * 和回调类似
 * 
 * @author yanghao
 * 
 */
public class JdbcTemp {

    public Object doSql(CallBack_DoSqlWithConn_abs callback, String sql) {
        // 这里也可以把result放到callback里面，callback再提供个getResult()，
        // 这样main函数的调用者那里可以用callback.getResult()获取返回值
        Connection conn = ConnectionUtil.createConnection();
        Object result = callback.doSql(conn, sql);
        ConnectionUtil.destroyConnection(conn);

        return result;
    }

    public static void main(String[] args) {
        JdbcTemp jdbcTemp = new JdbcTemp();

        CallBack_DoSqlWithConn_abs callback = new CallBack_DoSqlWithConn_abs() {
            @Override
            public Object doSql(Connection conn, String sql) {
                return sql;
            }
        };

        String sql = "sql ....";
        System.out.println(jdbcTemp.doSql(callback, sql));
    }
}
