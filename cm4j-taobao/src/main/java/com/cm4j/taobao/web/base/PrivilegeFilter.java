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

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.WebUtils;

import com.cm4j.taobao.web.login.UserSession;

/**
 * 权限过滤器
 * 
 * @author yang.hao
 * @since 2011-7-27 下午06:14:31
 * 
 */
public class PrivilegeFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;

		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(req, UserSession.SESSION_NAME);

		// 校验权限
		VisitorPrivilege privilege = null;
//		userSession.getVisitor_privilege();
		String uri = req.getRequestURI();
		if (StringUtils.startsWith(uri, privilege.name())) {
			// 权限不对...
			req.setAttribute(BaseDispatchAction.MESSAGE_KEY, "您的权限不够，无法使用此功能，请购买高级版本！");
			req.getRequestDispatcher("error.jsp").forward(req, rep);
		} else {
			chain.doFilter(req, rep);
		}

	}

	@Override
	public void destroy() {
	}

}
