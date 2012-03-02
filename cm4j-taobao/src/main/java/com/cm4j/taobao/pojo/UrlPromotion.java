package com.cm4j.taobao.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * URL 促销
 * 
 * @author yang.hao
 * @since 2011-9-13 下午3:18:39
 */
@Entity()
@Table(name = "url_promotion", schema = "")
public class UrlPromotion {

	/**
	 * ID
	 */
	@Id
	@Column(name = "promotion_id")
	private Long promotionId;

	/**
	 * 优惠标题，显示在宝贝详情页面的优惠图标的tip。
	 */
	@Column(name = "promotion_title")
	private String promotionTitle;

	/**
	 * 所属用户ID
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 商品IDs
	 */
	@Column(name = "num_iids")
	private String numIids;

	/**
	 * URL展示样式
	 */
	@Column(name = "promotion_style")
	private String promotionStyle;
	
	public enum URL_Promotion_Style {
		s_160_240;
	}

	/**
	 * 创建日期
	 */
	@Column(name = "create_date")
	private Date createDate;

	public enum Status {
		valid, invalid
	}

	/**
	 * 状态
	 */
	@Column(name = "status")
	private String status;

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	public String getPromotionTitle() {
		return promotionTitle;
	}

	public void setPromotionTitle(String promotionTitle) {
		this.promotionTitle = promotionTitle;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNumIids() {
		return numIids;
	}

	public void setNumIids(String numIids) {
		this.numIids = numIids;
	}

	public String getPromotionStyle() {
		return promotionStyle;
	}

	public void setPromotionStyle(String promotionStyle) {
		this.promotionStyle = promotionStyle;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
