package com.cm4j.core.utils;

import org.junit.Assert;
import org.junit.Test;

import com.cm4j.core.utils.EmailValidator;

public class EmailValidatorTest {

    @Test
    public void testValidatEmail() {
        Assert.assertTrue(new EmailValidator().isValid("s@s.com"));
        Assert.assertFalse(new EmailValidator().isValid("s@.com"));
    }
}
