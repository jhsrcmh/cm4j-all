package com.cm4j.taobao.web.idcontext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cm4j.taobao.api.idcontext.IdentityContext;
import com.cm4j.taobao.api.utils.APIConstants;
import com.cm4j.taobao.web.base.BaseDispatchAction;

@Controller
@SessionAttributes(types = { UserSession.class })
public class IdContextAction extends BaseDispatchAction {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 登陆系统
	 * 
	 * @param modelMap
	 * @param top_session
	 * @param top_parameters
	 * @param top_sign
	 * @return
	 */
	@RequestMapping("/user_login")
	public String user_login(ModelMap modelMap, String top_session, String top_parameters, String top_sign) {
		boolean result = false;
		try {
			result = IdentityContext.verifyTopResponse(top_parameters, top_session, top_sign, APIConstants.getAppKey(),
					APIConstants.getAppSecret());
		} catch (Exception e) {
			logger.error("用户登陆异常", e);
		}

		if (!result) {
			logger.error("用户登陆失败，重新跳到登陆认证页");
			modelMap.remove("userSession");
			return "redirect:" + getLoginUrl();
		}

		UserSession userSession = new UserSession();
		userSession.setTop_session(top_session);
		userSession.setVisitor_id(IdentityContext.resolveParameters(top_parameters, APIConstants.VISITOR_ID));
		userSession.setVisitor_nick(IdentityContext.resolveParameters(top_parameters, APIConstants.VISITOR_NICK));
		userSession.setVisitor_role(IdentityContext.resolveParameters(top_parameters, APIConstants.VISITOR_ROLE));
		modelMap.put("userSession", userSession);

		return "success";
	}

	/**
	 * 获取应用的登陆地址
	 * 
	 * @return
	 */
	public static String getLoginUrl() {
		StringBuilder sb = new StringBuilder(APIConstants.getTaobaoServiceUrl()).append("/container?appkey=").append(
				APIConstants.getAppKey());
		return sb.toString();
	}
}
