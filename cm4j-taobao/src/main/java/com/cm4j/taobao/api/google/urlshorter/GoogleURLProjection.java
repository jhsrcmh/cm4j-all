package com.cm4j.taobao.api.google.urlshorter;

/**
 * google访问返回信息类型<br />
 * GET https://www.googleapis.com/urlshortener/v1/url?key={key}&shortUrl={shortUrl}&projection={projection?}
 * 
 * @author yang.hao
 * @since 2011-9-8 下午10:36:03
 */
public enum GoogleURLProjection {
	/**
	 * returns the creation timestamp and all available analytics
	 */
	FULL,
	/**
	 * returns only click counts
	 */
	ANALYTICS_CLICKS,
	/**
	 * returns only top string counts (e.g. referrers, countries, etc)
	 */
	ANALYTICS_TOP_STRINGS
}
