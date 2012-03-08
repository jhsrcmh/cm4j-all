package com.woniu.network.protocol;

import junit.framework.Assert;

import org.junit.Test;

import com.woniu.network.protocol.xmlmodel.XMLModel;

public class ProtocolConfigTest {

	@Test
	public void getProtocolConfigurationTest() throws Exception {
		XMLModel model = ProtocolConfig.getProtocolConfiguration("protocol.xml");
		Assert.assertEquals("UTF-8", model.getStringEncoding());
	}
}
