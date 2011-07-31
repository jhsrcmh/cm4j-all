package com.cm4j.taobao.api.googds.postage;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.cm4j.taobao.api.common.APICaller;
import com.cm4j.taobao.exception.ValidationException;
import com.cm4j.taobao.utils.ValidateUtils;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Postage;
import com.taobao.api.request.PostageAddRequest;
import com.taobao.api.response.PostageAddResponse;

/**
 * API包：taobao.postage...
 * 
 * @author yang.hao
 * @since 2011-7-30 下午03:11:07
 * 
 */
@Service
public class PostageService {

	/**
	 * taobao.postage.add 添加邮费模板
	 * 
	 * @param request
	 * @param sessionKey
	 * @return
	 * @throws ApiException
	 * @throws ValidationException
	 */
	public Postage add(PostageAddRequest request, String sessionKey) throws ApiException, ValidationException {
		checkAddRequest(request);

		PostageAddResponse response = APICaller.call(request, sessionKey);
		APICaller.resolveResponseException(response);

		return response.getPostage();
	}

	/**
	 * 验证add request<br />
	 * 注意：postage_mode_types,postage_mode_dests,postage_mode_prices,
	 * postage_mode_increases未验证，调用时请注意按文档传入
	 * 
	 * @param request
	 * @throws ValidationException
	 */
	private void checkAddRequest(PostageAddRequest request) throws ValidationException {
		int nameLength = StringUtils.length(request.getName());
		if (nameLength < 1 || nameLength > 50) {
			throw new ValidationException("邮费模板名称长度范围为[1-50]");
		}

		if (StringUtils.isNotBlank(request.getMemo()) && StringUtils.length(request.getMemo()) > 200) {
			throw new ValidationException("邮费模板备注长度范围为[0-200]");
		}

		if (StringUtils.isBlank(request.getPostPrice()) && StringUtils.isBlank(request.getExpressPrice())
				&& StringUtils.isBlank(request.getEmsPrice())) {
			throw new ValidationException("平邮,快递公司,EMS请至少选择一个");
		}
		if (StringUtils.isNotBlank(request.getPostPrice()) && !ValidateUtils.validateDecimal(request.getPostPrice(), 1)) {
			throw new ValidationException("默认平邮费用不合法");
		}
		if (StringUtils.isNotBlank(request.getPostIncrease())
				&& !ValidateUtils.validateDecimal(request.getPostIncrease(), 1)) {
			throw new ValidationException("默认平邮加价费用不合法");
		}
		if (StringUtils.isNotBlank(request.getExpressPrice())
				&& !ValidateUtils.validateDecimal(request.getExpressPrice(), 1)) {
			throw new ValidationException("默认快递费用不合法");
		}
		if (StringUtils.isNotBlank(request.getExpressIncrease())
				&& !ValidateUtils.validateDecimal(request.getExpressIncrease(), 1)) {
			throw new ValidationException("默认快递加价费用不合法");
		}
		if (StringUtils.isNotBlank(request.getEmsPrice()) && !ValidateUtils.validateDecimal(request.getEmsPrice(), 1)) {
			throw new ValidationException("默认EMS费用不合法");
		}
		if (StringUtils.isNotBlank(request.getEmsIncrease())
				&& !ValidateUtils.validateDecimal(request.getEmsIncrease(), 1)) {
			throw new ValidationException("默认EMS加价费用不合法");
		}
		// postage_mode_types,postage_mode_dests,postage_mode_prices,postage_mode_increases未验证
	}
}
