package com.cm4j.taobao.web.base;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.WebUtils;

import com.cm4j.taobao.web.login.LoginAction;
import com.cm4j.taobao.web.login.UserSession;

public class LoginFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.debug("登陆过滤器init...");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;

		// 登录校验
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(req, UserSession.SESSION_NAME);
		if (userSession == null) {
			String url = LoginAction.getLoginUrl();
			logger.debug("用户没有登陆,跳转页面:{}", url);
			if ("true".equals(req.getParameter("is_json"))) {
				BaseDispatchAction.writeJson(rep, new ResultObject(-1, "用户未登陆", url));
				return;
			} else {
				rep.sendRedirect(url);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		logger.debug("登陆过滤器destory...");
	}

}
