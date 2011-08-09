package com.cm4j.taobao.dao;

import org.springframework.stereotype.Repository;

import com.cm4j.dao.hibernate.HibernateDao;
import com.cm4j.taobao.pojo.UserInfo;

@Repository
public class UserInfoDao extends HibernateDao<UserInfo, Long> {

}
