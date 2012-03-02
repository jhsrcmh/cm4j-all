package com.cm4j.taobao.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * 日期枚举
 * 
 * @author yang.hao
 * @since 2011-9-13 下午4:24:01
 */
public enum DATE_ENUM implements Function<Date> {
	NOW(0), FOREVER(100);
	private int year;

	private DATE_ENUM(int year) {
		this.year = year;
	}

	@Override
	public Date apply() {
		return DateUtils.addYears(Calendar.getInstance().getTime(), this.year);
	}

}