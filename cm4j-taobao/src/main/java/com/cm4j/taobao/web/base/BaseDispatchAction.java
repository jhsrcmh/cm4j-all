package com.cm4j.taobao.web.base;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.cm4j.core.utils.JsonBinder;
import com.cm4j.taobao.api.common.APICaller;
import com.cm4j.taobao.api.common.APIConstants;
import com.cm4j.taobao.exception.ValidationException;
import com.cm4j.taobao.web.login.UserSession;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoRequest;
import com.taobao.api.TaobaoResponse;

public class BaseDispatchAction {

	public static final Logger logger = LoggerFactory.getLogger(BaseDispatchAction.class);

	protected JsonBinder jsonBinder = JsonBinder.NON_NULL;

	/**
	 * 错误页面
	 */
	public static String ERROR_PAGE = "error";
	/**
	 * 放到request的错误的key
	 */
	public static String ERROR_KEY = "msg";
	/**
	 * 成功提示页面
	 */
	public static String SUCCESS_PAGE = "success";
	

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
	protected void addRequestErrorMessage (Object obj){
		getRequest().setAttribute(ERROR_KEY, obj);
	}

	/**
	 * 获取应用的登陆地址
	 * 
	 * @return
	 */
	public static String getLoginUrl() {
		StringBuilder sb = new StringBuilder(APIConstants.getTaobaoContainerUrl()).append("/container?appkey=").append(
				APIConstants.getAppKey());
		return sb.toString();
	}

	/**
	 * 执行淘宝应用API
	 * 
	 * @param request
	 * @param needSession
	 *            是否需要sessionKey,是则从session中获取sessionKey
	 * @return
	 * @throws ApiException
	 */
	public <T extends TaobaoResponse> T exec(TaobaoRequest<T> request, boolean needSession) throws ApiException {
		String sessionKey = null;
		if (needSession) {
			sessionKey = getSessionKey();
		}
		return APICaller.call(request, sessionKey);
	}

	/**
	 * 获取session中的淘宝用户sessionKey
	 * 
	 * @return
	 * @throws ApiException
	 */
	public String getSessionKey() throws ApiException {
		String sessionKey = null;
		UserSession userSession = (UserSession) WebUtils.getSessionAttribute(getRequest(), UserSession.SESSION_NAME);
		if (userSession != null) {
			sessionKey = userSession.getTop_session();
		} else {
			throw new ApiException("can not find 'userSession' in session,please login first!");
		}
		return sessionKey;
	}

	/**
	 * 异常捕获
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler
	public ModelAndView validationExceptionHandle(ValidationException exception) {
		ModelAndView view = new ModelAndView(ERROR_PAGE);
		view.addObject("msg", "数据不合法：" + exception.getMessage());
		return view;
	}

	/**
	 * 异常捕获
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler
	public ModelAndView apiExceptionHandle(ApiException exception) {
		ModelAndView view = new ModelAndView(ERROR_PAGE);
		view.addObject("msg", "调用淘宝API异常：" + exception.getMessage());
		return view;
	}

	/**
	 * 异常捕获
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler
	public ModelAndView otherExceptionHandle(Exception exception) {
		ModelAndView view = new ModelAndView(ERROR_PAGE);
		view.addObject("msg", "其他异常：" + exception.getMessage());
		return view;
	}
}
