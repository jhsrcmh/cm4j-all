package com.cm4j.email.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cm4j.dao.batch.BatchDao;

@Repository
public class BatchDaoOperator extends BatchDao {

    public static final String INSERT_EMAIL_INBOX = "insert into email_inbox ( n_id , s_email  ) values((select email_inbox_sq.nextval from dual ),?)";

    public void batchInsertEmailInbox(List<Object[]> batchArgs) {
        super.batchUpdate(INSERT_EMAIL_INBOX, batchArgs);
    }

    public static final String INSERT_EMAIL_OUTBOX = "insert into email_outbox ( n_id , s_email , s_user_name ,s_password ,s_host_name ,n_port )"
            + " values ((select email_inbox_sq.nextval from dual ),?,?,?,?,?)";

    public void batchInsertEmailOutbox(List<Object[]> batchArgs) {
        super.batchUpdate(INSERT_EMAIL_OUTBOX, batchArgs);
    }
}
