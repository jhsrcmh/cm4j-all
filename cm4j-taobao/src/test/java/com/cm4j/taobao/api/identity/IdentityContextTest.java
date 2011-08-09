package com.cm4j.taobao.api.identity;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Test;

public class IdentityContextTest {

	@Test
	public void testVerifyVersion() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		boolean flag = IdentityContext.verifyVersionResponse("c1927d998894b85dfab19cbcc8aee93b", "93996", 51865L,
				"1287547223869", 1, "639B98FFD3B33D275238FA5B476AAD52");
		Assert.assertTrue(flag);
	}
}
