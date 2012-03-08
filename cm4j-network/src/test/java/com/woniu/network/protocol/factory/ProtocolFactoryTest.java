package com.woniu.network.protocol.factory;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.junit.Test;

import com.woniu.network.exception.ProtocolParamValidationException;
import com.woniu.network.protocol.ConstructedMessage;

public class ProtocolFactoryTest {

	@Test
	public void createProtocolTest() throws ProtocolParamValidationException, JAXBException, IOException {

		ProtocolFactory factory = new ProtocolFactory();
		ConstructedMessage message = factory.getConstructedMessage(0x1001);
		
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		
//		// 创建效率测试 - 90881ms
		int max = 1000000;
		ConstructedMessage protocol2 ;
		long start = System.nanoTime();
		for (int i = 0; i < max; i++) {
			protocol2 = factory.getConstructedMessage(0x1001);
		}
		long end = System.nanoTime();
		System.out.println((end - start) / 1000000);
//		
//		// 简单对象创建 - 19ms
//		ImprestInfoProtocolParam param2;
//		long startParam = System.nanoTime();
//		for (int i = 0; i < max; i++) {
//			param2 = new ImprestInfoProtocolParam();
//		}
//		long endParam = System.nanoTime();
//		System.out.println((endParam - startParam) / 1000000);
	}
}
