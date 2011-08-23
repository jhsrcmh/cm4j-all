package com.cm4j.taobao.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 定向优惠策略活动表
 * 
 * @author yang.hao
 * @since 2011-8-17 下午04:34:40
 *
 */
@Entity()
@Table(name = "promotion_ploy", schema = "")
public class PromotionPloy {
	
	/**
	 * 优惠ID
	 */
	@Id
	@Column(name = "promotion_id")
	private Long promotionId;
	
	/**
	 * 商品数字ID
	 */
	@Column(name = "num_iids")
	private String numIids;
	
	/**
	 * 优惠标题，显示在宝贝详情页面的优惠图标的tip。
	 */
	@Column(name = "promotion_title")
	private String promotionTitle;

	/**
	 * 优惠描述
	 */
	@Column(name = "promotion_desc")
	private String promotionDesc;

	/**
	 * 优惠类型，PRICE表示按价格优惠，DISCOUNT表示按折扣优惠
	 */
	@Column(name = "discount_type")
	private String discountType;

	/**
	 * 优惠额度
	 */
	@Column(name = "discount_value")
	private String discountValue;
	
	/**
	 * 优惠开始日期
	 */
	@Column(name = "start_date")
	private Date startDate;
	
	/**
	 * 优惠结束日期
	 */
	@Column(name = "end_date")
	private Date endDate;

	public enum Status {
		ACTIVE,UNACTIVE
	}
	
	/**
	 * 优惠策略状态，ACTIVE表示有效，UNACTIVE表示无效
	 */
	@Column(name = "status")
	private String status;

	/**
	 * 对应的人群标签ID
	 */
	@Column(name = "tag_id")
	private Long tagId;
	
	/**
	 * 减价件数，1只减一件，0表示多件
	 */
	@Column(name = "decrease_num")
	private Long decreaseNum;

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

	public String getPromotionDesc() {
		return promotionDesc;
	}

	public void setPromotionDesc(String promotionDesc) {
		this.promotionDesc = promotionDesc;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public String getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(String discountValue) {
		this.discountValue = discountValue;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public Long getDecreaseNum() {
		return decreaseNum;
	}

	public void setDecreaseNum(Long decreaseNum) {
		this.decreaseNum = decreaseNum;
	}

	public String getNumIids() {
		return numIids;
	}

	public void setNumIids(String numIids) {
		this.numIids = numIids;
	}
}
