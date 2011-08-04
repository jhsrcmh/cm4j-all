package com.cm4j.taobao.web.login.authentication;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cm4j.taobao.api.common.APICaller;
import com.cm4j.taobao.web.login.UserSession;

/**
 * 用户拥有的权限 用于获取登录用户信息和用户所具有的角色
 * 
 * @author yang.hao
 * @since 2011-8-2 下午02:34:31
 * 
 */
@Service
public class SecurityUserDetailService implements UserDetailsService {

	/**
	 * @param json
	 *            UserSession对象的json串
	 */
	@Override
	public UserDetails loadUserByUsername(String json) throws UsernameNotFoundException, DataAccessException {
		return APICaller.jsonBinder.fromJson(json, UserSession.class);
	}
}
