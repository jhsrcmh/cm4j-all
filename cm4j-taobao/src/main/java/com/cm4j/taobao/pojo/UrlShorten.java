package com.cm4j.taobao.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * google网址缩短记录表
 * @author yang.hao
 * @since 2011-9-13 下午5:10:06
 */
@Entity()
@Table(name = "url_shorten", schema = "")
public class UrlShorten {

	/**
	 * ID
	 */
	@Id
	@Column(name = "url_id")
	private Long urlId;

	/**
	 * 所属用户ID
	 */
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "long_url")
	private String longUrl;

	@Column(name = "short_url")
	private String shortUrl;

	public enum ShortenType {
		system, manual
	}

	@Column(name = "shorten_type")
	private String shortenType;

	@Column(name = "visit_num")
	private Long visitNum;

	public enum Status {
		valid, invalid
	}

	@Column(name = "status")
	private String status;

	public Long getUrlId() {
		return urlId;
	}

	public void setUrlId(Long urlId) {
		this.urlId = urlId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public String getShortenType() {
		return shortenType;
	}

	public void setShortenType(String shortenType) {
		this.shortenType = shortenType;
	}

	public Long getVisitNum() {
		return visitNum;
	}

	public void setVisitNum(Long visitNum) {
		this.visitNum = visitNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
