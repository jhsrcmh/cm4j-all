package com.cm4j.taobao.web.google.urlshorter;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm4j.taobao.api.google.urlshorter.GoogleURLProjection;
import com.cm4j.taobao.api.google.urlshorter.GoogleURLShorter;
import com.cm4j.taobao.api.google.urlshorter.URLShorterException;
import com.cm4j.taobao.exception.ValidationException;
import com.cm4j.taobao.web.base.BaseDispatchAction;
import com.google.common.base.Preconditions;

@Controller
@RequestMapping("/secure/google")
public class GoogleURLShorterAction extends BaseDispatchAction {

	/**
	 * 缩短网址
	 * 
	 * @param longUrl
	 * @return
	 * @throws ValidationException
	 */
	@RequestMapping(value = "/shorten", method = RequestMethod.POST)
	public @ResponseBody
	String shorten(String longUrl) throws ValidationException {
		Preconditions.checkArgument(StringUtils.isNotBlank(longUrl), "参数longUrl不允许为空");

		try {
			return GoogleURLShorter.shorten(longUrl);
		} catch (URLShorterException e) {
			logger.error("缩短网址API调用异常", e);
			throw new ValidationException("缩短网址API调用异常");
		}
	}

	/**
	 * 统计网址点击
	 * 
	 * @param shortUrl
	 * @return
	 * @throws ValidationException
	 */
	@RequestMapping(value = "/shortUrl", method = RequestMethod.POST)
	public @ResponseBody
	String staticUrl(String shortUrl) throws ValidationException {
		Preconditions.checkArgument(StringUtils.isNotBlank(shortUrl), "参数shortUrl不允许为空");

		try {
			return GoogleURLShorter.longenAndReturnJson(shortUrl, GoogleURLProjection.ANALYTICS_CLICKS);
		} catch (URLShorterException e) {
			logger.error("统计网址API调用异常", e);
			throw new ValidationException("统计网址API调用异常");
		}
	}
}
