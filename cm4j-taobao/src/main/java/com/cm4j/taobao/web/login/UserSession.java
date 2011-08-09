package com.cm4j.taobao.web.login;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cm4j.taobao.web.base.VisitorPrivilege;

public class UserSession implements UserDetails {

	private static final long serialVersionUID = 1L;

	/**
	 * 存放于session中的Name
	 */
	public static final String SESSION_NAME = "userSession";

	private String top_session;
	private Long visitor_id;
	private String visitor_nick;
	private String visitor_role;
	private VisitorPrivilege visitor_privilege;
	/**
	 * 用户权限
	 */
	private Collection<GrantedAuthority> authorities;

	public String getTop_session() {
		return top_session;
	}

	public void setTop_session(String top_session) {
		this.top_session = top_session;
	}

	public String getVisitor_nick() {
		return visitor_nick;
	}

	public void setVisitor_nick(String visitor_nick) {
		this.visitor_nick = visitor_nick;
	}

	public String getVisitor_role() {
		return visitor_role;
	}

	public void setVisitor_role(String visitor_role) {
		this.visitor_role = visitor_role;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return this.visitor_nick;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Long getVisitor_id() {
		return visitor_id;
	}

	public void setVisitor_id(Long visitor_id) {
		this.visitor_id = visitor_id;
	}

	public VisitorPrivilege getVisitor_privilege() {
		return visitor_privilege;
	}

	public void setVisitor_privilege(VisitorPrivilege visitor_privilege) {
		this.visitor_privilege = visitor_privilege;
	}
}
