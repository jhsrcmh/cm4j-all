package com.cm4j.taobao.utils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 暂未用到，需测试？
 * @author yang.hao
 * @since 2011-9-6 下午5:20:55
 */
public class UtilDateConverter extends PropertyEditorSupport {
	private String format = "yyyy-MM-dd";

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(text);
		} catch (ParseException e) {
			System.err.println("转化失败");
			e.printStackTrace();
		}
		this.setValue(date);
	}

	public void setFormat(String format) {
		this.format = format;
	}

}