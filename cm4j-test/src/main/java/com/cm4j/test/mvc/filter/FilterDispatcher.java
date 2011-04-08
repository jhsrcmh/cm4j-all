package com.cm4j.test.mvc.filter;

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

public class FilterDispatcher implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		// 转换
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		// 传递到其他过滤器处理
		chain.doFilter(req, resp);
		String actionName = getActionNameFromURI(req);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

	private String getActionNameFromURI (HttpServletRequest request){
		String path = request.getRequestURI();
		String actionName = StringUtils.substringAfterLast(path, "/");
		return actionName;
	}
}
