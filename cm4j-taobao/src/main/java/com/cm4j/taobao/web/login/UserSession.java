package com.cm4j.taobao.web.login;

import com.cm4j.taobao.api.common.VisitorPrivilege;

public class UserSession {

	/**
	 * 存放于session中的Name
	 */
	public static final String SESSION_NAME = "userSession";
	
	private String top_session;
	private String visitor_id;
	private String visitor_nick;
	private String visitor_role;
	private VisitorPrivilege visitor_privilege;

	public String getTop_session() {
		return top_session;
	}

	public void setTop_session(String top_session) {
		this.top_session = top_session;
	}

	public String getVisitor_id() {
		return visitor_id;
	}

	public void setVisitor_id(String visitor_id) {
		this.visitor_id = visitor_id;
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

	public VisitorPrivilege getVisitor_privilege() {
		return visitor_privilege;
	}

	public void setVisitor_privilege(VisitorPrivilege visitor_privilege) {
		this.visitor_privilege = visitor_privilege;
	}

}
