package com.cm4j.taobao.web.base;

/**
 * json串返回结果
 * 
 * @author yang.hao
 * @since 2011-8-13 下午09:23:32
 * 
 */
public class ResultObject {

	private int code;
	private String message;
	private Object objInfo;

	public ResultObject(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public ResultObject(int code, String message, Object objInfo) {
		super();
		this.code = code;
		this.message = message;
		this.objInfo = objInfo;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObjInfo() {
		return objInfo;
	}

	public void setObjInfo(Object objInfo) {
		this.objInfo = objInfo;
	}

}
