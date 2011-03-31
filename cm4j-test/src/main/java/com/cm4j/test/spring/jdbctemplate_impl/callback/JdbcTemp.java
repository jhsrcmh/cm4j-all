package com.cm4j.test.spring.jdbctemplate_impl.callback;

import com.cm4j.test.spring.jdbctemplate_impl.Connection;
import com.cm4j.test.spring.jdbctemplate_impl.ConnectionUtil;

/**
 * <pre>
 * jdbc实现了公用DAL，其步骤为：
 * 1.创建连接
 * 2.使用连接执行
 * 3.销毁连接
 * 
 * spring帮我们实现了1、3步，我们只需要实现第二步，这是如何实现的呢？
 * 即把变化的部分交由用户的jdbcTemplate实现，不变的部分由spring内部完成
 * </pre>
 * @author yanghao
 *
 */
public class JdbcTemp {

    public Object doSql(CallBack_DoSqlWithConn callback) {
        // 创建连接
        Connection conn = ConnectionUtil.createConnection();
        // 执行sql
        Object result = callback.doSql(conn);
        // 关闭连接
        ConnectionUtil.destroyConnection(conn);
        return result;
    }
    
    public static void main(String[] args) {
        JdbcTemp temp = new JdbcTemp();
        CallBack_DoSqlWithConn callback = new CallBack_DoSqlWithConn() {
            @Override
            public Object doSql(Connection conn) {
                return "sql result";
            }
        };
        Object result = temp.doSql(callback);
        System.out.println(result);
    }
}
