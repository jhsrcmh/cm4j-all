package com.cm4j.taobao.web.login.authentication;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

//@Service
public class SecurityFilter extends AbstractSecurityInterceptor implements Filter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private FilterInvocationSecurityMetadataSource securityMetadataSource; 
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.debug("SecurityFilter init()...");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		FilterInvocation invocation = new FilterInvocation(request, response, chain);  
        invoke(invocation); 
	}
	
	public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {  
        InterceptorStatusToken token = super.beforeInvocation(filterInvocation);  
        try {  
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());  
        } finally {  
            super.afterInvocation(token, null);  
        }  
    } 

	@Override
	public void destroy() {
		logger.debug("SecurityFilter destroy()...");
	}

	@Override
	public Class<? extends Object> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource; 
	}

	public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
		return securityMetadataSource;
	}

	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}

}
