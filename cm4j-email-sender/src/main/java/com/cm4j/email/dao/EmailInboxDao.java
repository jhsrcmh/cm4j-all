package com.cm4j.email.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.cm4j.email.pojo.EmailInbox;

@Repository
public class EmailInboxDao extends MyBatisDao {

	private static final String STMT_QUERY_INBOX_TO_SEND = "queryInboxToSend";

	/**
	 * 查询一定量的收件箱来用于发送
	 * 
	 * @param amount
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EmailInbox> queryInboxToSend(int amount) {
		RowBounds bounds = new RowBounds(RowBounds.NO_ROW_OFFSET, amount);
		return getSqlSession().selectList(STMT_QUERY_INBOX_TO_SEND, EmailInbox.STATE_TO_SEND, bounds);
	}

}
