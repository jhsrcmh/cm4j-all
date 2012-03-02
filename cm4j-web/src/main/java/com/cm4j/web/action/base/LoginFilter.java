package com.cm4j.web.action.base;

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

import com.cm4j.web.pojo.UserInfo;
import com.cm4j.web.utils.WebConstants;

public class LoginFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final String SESSION_KEY = "session_key";

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
		UserInfo userInfo = (UserInfo) WebUtils.getSessionAttribute(req, SESSION_KEY);
		if (userInfo == null) {
			String url = WebConstants.getLoginUrl();
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
