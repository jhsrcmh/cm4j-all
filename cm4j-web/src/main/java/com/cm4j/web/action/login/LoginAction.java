package com.cm4j.web.action.login;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.cm4j.dao.hibernate.HibernateDao;
import com.cm4j.web.action.base.BaseDispatchAction;
import com.cm4j.web.action.base.LoginFilter;
import com.cm4j.web.exception.BusinessException;
import com.cm4j.web.pojo.UserInfo;

/**
 * 登录action，默认除/unsafe以外所有的action都需要登录
 * 
 * @author yang.hao
 * @since 2011-7-27 下午04:07:38
 * 
 */
@Controller
public class LoginAction extends BaseDispatchAction {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private HibernateDao<UserInfo, Long> userInfoDao;

	/**
	 * 登陆系统
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping("/user_login/{userName}/{pwd}")
	@ResponseBody
	public UserInfo user_login(@PathVariable String userName, @PathVariable String pwd) throws BusinessException {
		userName = StringUtils.upperCase(userName);
		UserInfo userInfo = userInfoDao.findByProperty("userName", userName);
		if (userInfo == null) {
			throw new BusinessException("用户帐号不存在");
		}
		if (!userInfo.getPwd().equals(pwd)) {
			throw new BusinessException("用户密码不一致");
		}
		WebUtils.setSessionAttribute(getRequest(), LoginFilter.SESSION_KEY, userInfo);
		return userInfo;
	}
}
