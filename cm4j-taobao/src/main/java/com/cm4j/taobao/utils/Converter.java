package com.cm4j.taobao.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class Converter {

	/**
	 * 将List的String类型转换为List的Long类型
	 * 
	 * @param list
	 * @return
	 */
	public static List<Long> typeConvert(Iterable<String> list) {
		List<Long> result = new ArrayList<Long>();
		for (String element : list) {
			if (StringUtils.isNotBlank(element)) {
				result.add(NumberUtils.toLong(element));
			}
		}
		return result;
	}
}
