package com.cm4j.taobao.service.google.urlshorter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import com.cm4j.dao.hibernate.HibernateDao;
import com.cm4j.taobao.pojo.UrlPromotion;
import com.cm4j.taobao.pojo.UrlPromotion.URL_Promotion_Style;
import com.cm4j.taobao.utils.DATE_ENUM;

/**
 * 网址推广
 * 
 * @author yang.hao
 * @since 2011-9-13 下午2:53:01
 */
public class UrlPromotionService {

	@Resource
	private HibernateDao<UrlPromotion, Long> urlPromotionDao;
	@Autowired
	private TaskExecutor taskExecutor;

	/**
	 * 添加URL推广活动
	 * 
	 * @param user_id
	 * @param promotion_title
	 * @param num_iids
	 * @param style
	 */
	public void url_promotion_add (Long user_id,String promotion_title,String num_iids,URL_Promotion_Style style){
		UrlPromotion promotion = new UrlPromotion();
		promotion.setCreateDate(DATE_ENUM.NOW.apply());
		promotion.setNumIids(num_iids);
		promotion.setUserId(user_id);
		promotion.setPromotionStyle(style.name());
		promotion.setPromotionTitle(promotion_title);
		promotion.setStatus(UrlPromotion.Status.valid.name());
		urlPromotionDao.save(promotion);
		
		// todo 生成
	}
	
	
}
