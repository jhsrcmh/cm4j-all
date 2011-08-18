package com.cm4j.taobao.web.marketing.promotion;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cm4j.dao.hibernate.HibernateDao;
import com.cm4j.taobao.api.marketing.promotion.PromotionAPI;
import com.cm4j.taobao.exception.ValidationException;
import com.cm4j.taobao.pojo.PromotionPloy;
import com.cm4j.taobao.pojo.PromotionPloy.PromotionPloyStatus;
import com.cm4j.taobao.web.base.BaseDispatchAction;
import com.cm4j.taobao.web.base.ResultObject;
import com.google.common.base.Joiner;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Promotion;
import com.taobao.api.request.MarketingPromotionAddRequest;
import com.taobao.api.request.MarketingPromotionUpdateRequest;

@Controller
@RequestMapping("/secure/promotion")
public class PromotionAction extends BaseDispatchAction {

	@Resource
	private HibernateDao<PromotionPloy, Long> promotionPloyDao;

	/**
	 * 跳转到增加商品定向优惠策略
	 * 
	 * @return
	 */
	@RequestMapping("/prepare")
	public String prepare() {
		return "/promotion/add";
	}

	/**
	 * 设置商品定向优惠策略
	 * 
	 * @param request
	 * @throws ValidationException
	 * @throws ApiException
	 */
	@RequestMapping("/add")
	public String add(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endTime, MarketingPromotionAddRequest request)
			throws ValidationException, ApiException {
		request.setStartDate(startTime);
		request.setEndDate(endTime);
		Map<String, Object> result = PromotionAPI.add(request, super.getSessionKey());

		if (result != null) {
			Long[] ids = new Long[] {};
			Long promotionId = 0L;

			@SuppressWarnings("unchecked")
			List<Promotion> promotions = (List<Promotion>) result.get("promotions");
			if (CollectionUtils.isEmpty(promotions)) {
				throw new ValidationException("没有成功设置商品定向优惠活动");
			}

			for (Promotion promotion : promotions) {
				Long numIid = promotion.getNumIid();
				ids = (Long[]) ArrayUtils.add(ids, numIid);
				promotionId = promotion.getPromotionId();
			}

			PromotionPloy ploy = new PromotionPloy();
			ploy.setNumIids(Joiner.on(",").join(ids));
			ploy.setPromotionId(promotionId);
			ploy.setPromotionTitle(request.getPromotionTitle());
			ploy.setPromotionDesc(request.getPromotionDesc());
			ploy.setDiscountType(request.getDiscountType());
			ploy.setDiscountValue(request.getDiscountValue());
			ploy.setDecreaseNum(request.getDecreaseNum());
			ploy.setStartDate(request.getStartDate());
			ploy.setEndDate(request.getEndDate());
			ploy.setTagId(request.getTagId());
			ploy.setStatus(PromotionPloyStatus.ACTIVE.name());

			try {
				promotionPloyDao.save(ploy);
			} catch (DataAccessException e) {
				logger.error("定向优惠活动保存异常", e);
				throw new ValidationException("定向优惠活动保存异常，请重试");
			}
		}

		return "redirect:list";
	}

	/**
	 * 修改商品定向优惠策略
	 * 
	 * @param request
	 * @throws ValidationException
	 * @throws ApiException
	 */
	@RequestMapping("/update")
	public void update(MarketingPromotionUpdateRequest request) throws ValidationException, ApiException {
		// todo 是否要判断？
		//		PromotionPloy promotionPloy = promotionPloyDao.findById(request.getPromotionId());
		//		if (PromotionPloyStatus.UNACTIVE.name().equals(promotionPloy.getStatus())){
		//			throw new ValidationException("此活动已被禁用，无法进行修改操作！");
		//		}
		Map<String, Object> result = PromotionAPI.update(request, super.getSessionKey());
		logger.debug("result:{}", result);
	}

	/**
	 * 获取商品定向优惠策略列表
	 * 
	 * @param num_iid
	 *            必须 - 商品数字ID 。根据该ID查询商品下通过第三方工具设置的所有优惠策略
	 * @param fields
	 *            必须 - 需返回的优惠策略结构字段列表，不填则默认显示Promotion所有的字段
	 * @param status
	 *            非必须 - 可选值：ACTIVE(有效)，UNACTIVE(无效)，若不传或者传入其他值，则默认查询全部
	 * @param tag_id
	 *            非必须 - 标签ID
	 * @return
	 * @throws ValidationException
	 * @throws ApiException
	 */
	@RequestMapping("/get")
	public @ResponseBody
	Map<String, Object> get(String num_iid, String status, Long tag_id) throws ValidationException, ApiException {
		return PromotionAPI.get(num_iid, null, status, tag_id, super.getSessionKey());
	}

	/**
	 * 列表显示
	 * 
	 * @return
	 * @throws ValidationException
	 */
	@RequestMapping("/list")
	public ModelAndView list() throws ValidationException {
		List<PromotionPloy> result = null;
		try {
			result = promotionPloyDao.findAll();
		} catch (DataAccessException e) {
			logger.error("定向优惠活动查询异常", e);
			throw new ValidationException("定向优惠活动查询异常，请重试！");
		}
		return new ModelAndView("/promotion/list", Collections.singletonMap("result", result));
	}

	/**
	 * 禁用活动
	 * 
	 * @param promotion_id
	 * @return
	 * @throws ApiException
	 * @throws ValidationException
	 */
	@RequestMapping("/unactive")
	public @ResponseBody
	ResultObject unactive(Long promotion_id) throws ApiException, ValidationException {
		if (PromotionAPI.delete(promotion_id, super.getSessionKey())) {
			try {
				promotionPloyDao.update("update PromotionPloy set status = ? where promotionId = ?", new Object[] {
						PromotionPloyStatus.UNACTIVE.name(), promotion_id });
			} catch (DataAccessException e) {
				logger.error("禁用定向优惠活动数据库异常", e);
				throw new ValidationException("禁用定向优惠活动数据库异常，请重试！");
			}
			return successResultObject("禁用活动成功");
		} else {
			throw new ValidationException("禁用活动失败，请重试");
		}
	}
}
