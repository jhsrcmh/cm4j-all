package com.cm4j.email.sender;

import javax.mail.MessagingException;

import org.junit.Test;

public class EmailSenderTest {

	private EmailSender emailSender = new EmailSender();

	@Test
	public void testEmailSend() throws MessagingException {
		SendBoxInfo sendBoxInfo = new SendBoxInfo();
		sendBoxInfo.setFrom("syniii@163.com");
		sendBoxInfo.setHost("smtp.163.com");
		sendBoxInfo.setUserName("syniii");
		sendBoxInfo.setPwd("930905");

		EmailContent sendContent = new EmailContent();
		sendContent.setContent("看看能否收到2");
		sendContent.setSubject("邮件收到了么，不行就再试试？");

		String[] to = new String[] { "syniii@sogou.com", "hao.yhao@gmail.com" };

		emailSender.sendEmail(sendBoxInfo, sendContent, to);
	}
}
