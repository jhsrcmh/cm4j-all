package com.cm4j.web.action.base;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cm4j.core.utils.JsonBinder;

public class BaseDispatchAction {

	public static final Logger logger = LoggerFactory.getLogger(BaseDispatchAction.class);

	public static final JsonBinder jsonBinder = JsonBinder.NON_NULL;
	
	/**
	 * 错误页面
	 */
	protected static String ERROR_PAGE = "/error";
	/**
	 * 放到request的消息key
	 */
	protected static String MESSAGE_KEY = "msg";
	/**
	 * 成功提示页面
	 */
	protected static String SUCCESS_PAGE = "/success";

	/**
	 * 获取request
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}

	/**
	 * 添加错误信息到request
	 * 
	 * @param obj
	 */
	protected void addRequestErrorMessage(Object obj) {
		getRequest().setAttribute(MESSAGE_KEY, obj);
	}

	/**
	 * 写json串
	 * 
	 * @param response
	 * @param obj
	 */
	public static void writeJson(ServletResponse response, Object obj) {
		write(response, jsonBinder.toJson(obj));
	}

	/**
	 * 写String
	 * 
	 * @param response
	 * @param content
	 */
	public static void write(ServletResponse response, String content) {
		PrintWriter printer = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			printer = response.getWriter();
			printer.write(content);
		} catch (IOException e) {
			logger.error("response write error", e);
		} finally {
			printer.close();
		}
	}

	/**
	 * 异常捕获
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler
	protected String otherExceptionHandle(Exception exception, HttpServletRequest request, HttpServletResponse response) {
		logger.error("action caught exception", exception);
		if (checkJsonException(exception, request, response)) {
			return null;
		}
		request.setAttribute(MESSAGE_KEY, "哎呦，系统抽风了，异常信息[" + exception.getMessage() + "]");
		return ERROR_PAGE;
	}

	/**
	 * 处理为json的异常请求
	 * 
	 * @param exception
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean checkJsonException(Exception exception, HttpServletRequest request, HttpServletResponse response) {
		if ("true".equals(request.getParameter("is_json"))) {
			writeJson(response, new ResultObject(0, exception.getMessage()));
			return true;
		}
		return false;
	}

	/**
	 * 组装成功ResultObject对象
	 */
	protected ResultObject successResultObject(String message) {
		return successResultObject(message, null);
	}

	/**
	 * 组装成功ResultObject对象
	 */
	protected ResultObject successResultObject(String message, Object obj) {
		if (obj == null) {
			return new ResultObject(1, message);
		} else {
			return new ResultObject(1, message, obj);
		}
	}
}
