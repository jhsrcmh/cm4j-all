package com.woniu.network.protocol.xmlmodel;

import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * @author yang.hao
 * @since 2011-10-27 上午9:21:36
 */
public class Messages {
    @XmlElement
    Message[] message;

    public Message[] getMessage() {
        return message == null ? new Message[0] : message;
    }

}