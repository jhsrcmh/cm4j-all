package com.cm4j.taobao.utils;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

public class ValidateUtilsTest {

	@Test
	public void validateDecimalTest(){
		Assert.assertThat(true, Is.is(ValidateUtils.validateDecimal("1.10", 2)));
		Assert.assertThat(true, Is.is(ValidateUtils.validateDecimal("0.1", 2)));
		Assert.assertThat(false, Is.is(ValidateUtils.validateDecimal("-0.1", 2)));
		Assert.assertThat(false, Is.is(ValidateUtils.validateDecimal("1.10", 1)));
	}
}
