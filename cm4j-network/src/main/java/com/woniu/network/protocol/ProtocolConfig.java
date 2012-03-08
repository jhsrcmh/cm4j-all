package com.woniu.network.protocol;

import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.woniu.network.protocol.xmlmodel.XMLModel;
import com.woniu.network.util.ClassPathResource;

public class ProtocolConfig {

	/**
	 * 解析xml文件 -> 对象
	 * 
	 * @param protocolLocation
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	public static XMLModel getProtocolConfiguration(String protocolLocation) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(XMLModel.class);
		Unmarshaller um = context.createUnmarshaller();
		return (XMLModel) um.unmarshal(new ClassPathResource(protocolLocation).getInputStream());
	}
}
