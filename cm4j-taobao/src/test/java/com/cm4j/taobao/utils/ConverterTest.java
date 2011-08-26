package com.cm4j.taobao.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;

public class ConverterTest {

	@Test
	public void typeConverterTest() {
		ArrayList<String> a = Lists.newArrayList("1", "2", "3");
		List<Long> b = Converter.typeConvert(a);
		Assert.assertEquals(a.size(), b.size());
	}
}
