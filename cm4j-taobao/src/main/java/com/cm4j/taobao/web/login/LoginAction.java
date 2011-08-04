package com.cm4j.taobao.web.login;

import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import com.cm4j.core.utils.JsonBinder;
import com.cm4j.taobao.api.common.APICaller;
import com.cm4j.taobao.api.common.APIConstants;
import com.cm4j.taobao.api.common.VisitorPrivilege;
import com.cm4j.taobao.api.identity.IdentityContext;
import com.cm4j.taobao.web.base.BaseDispatchAction;

/**
 * 登录action，默认除/unsafe以外所有的action都需要登录
 * 
 * @author yang.hao
 * @since 2011-7-27 下午04:07:38
 * 
 */
@Controller
public class LoginAction extends BaseDispatchAction {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 登陆系统
	 * 
	 * @param top_session
	 * @param top_parameters
	 * @param top_sign
	 * @param leaseId
	 *            租赁实体ID：应用ID或者应用包ID
	 * @param versionNo
	 *            版本编号： 1,初级；2,中级；3,高级；
	 * @return
	 */
	@RequestMapping("/user_login")
	public String user_login(String top_session, String top_parameters, String top_sign, Long leaseId, Integer versionNo) {
		boolean result = false;
		try {
			result = IdentityContext.verifyTopResponse(top_parameters, top_session, top_sign, APIConstants.getAppKey(),
					APIConstants.getAppSecret());
		} catch (Exception e) {
			logger.error("用户登陆异常", e);
		}

		if (!result) {
			logger.error("用户登陆失败，重新跳到登陆认证页");
			WebUtils.setSessionAttribute(getRequest(), UserSession.SESSION_NAME, null);
			return "redirect:" + getLoginUrl();
		}

		// 时间戳校验
		String ts = IdentityContext.resolveParameters(top_parameters, APIConstants.TIME_STAMP);
		Date now = APICaller.getTaobaoTime();
		Date last = DateUtils.addSeconds(now, -APIConstants.getApplicationTimeout());
		if (last.getTime() > NumberUtils.toLong(ts)) {
			logger.error("登陆超时：now:{},last:{}", now.getTime(), ts);
			WebUtils.setSessionAttribute(getRequest(), UserSession.SESSION_NAME, null);
			return "redirect:" + getLoginUrl();
		}

		UserSession userSession = new UserSession();
		userSession.setTop_session(top_session);
		userSession.setVisitor_id(IdentityContext.resolveParameters(top_parameters, APIConstants.VISITOR_ID));
		userSession.setVisitor_nick(IdentityContext.resolveParameters(top_parameters, APIConstants.VISITOR_NICK));
		userSession.setVisitor_role(IdentityContext.resolveParameters(top_parameters, APIConstants.VISITOR_ROLE));

		if (versionNo == null) {
			// todo 公网必须去掉，也就是所有的都必须校验版本
//			userSession.setVisitor_privilege(VisitorPrivilege.junior);
		} else {
			VisitorPrivilege privilege = VisitorPrivilege.getPrivilege(versionNo);
			if (privilege == null) {
				// 权限获取为空
				Model model = new BindingAwareModelMap();
				model.addAttribute(ERROR_KEY, "权限获取为空，请联系管理员");
				return ERROR_PAGE;
			}
//			userSession.setVisitor_privilege(privilege);
		}

		WebUtils.setSessionAttribute(getRequest(), UserSession.SESSION_NAME, userSession);

		logger.debug("用户登陆成功，userSession:{}", JsonBinder.NON_NULL.toJson(userSession));

		return SUCCESS_PAGE;
	}
}
