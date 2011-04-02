package com.cm4j.email.dao;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cm4j.dao.hibernate.HibernateDao;
import com.cm4j.email.pojo.EmailInbox;

@Repository
public class EmailInboxDao extends HibernateDao<EmailInbox, Long> {

	/**
	 * 查询一定量的收件箱来用于发送
	 * 
	 * @param amount
	 * @return
	 */
	public List<EmailInbox> queryInboxToSend(int amount) {
		return pageByProperty(Collections.singletonMap("state", (Object) EmailInbox.STATE_TO_SEND), 0, amount, "id",
				false);
	}

}
