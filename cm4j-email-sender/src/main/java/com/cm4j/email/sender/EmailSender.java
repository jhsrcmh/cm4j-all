package com.cm4j.email.sender;

import java.util.Arrays;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 邮件发送
 * 
 * @author Administrator
 * 
 */
public class EmailSender {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 系统退信如何处理？
	 * 
	 * @param sendBoxInfo
	 * @param sendContent
	 * @param to
	 * @throws MessagingException
	 *             信息异常
	 * @throws MailSendException
	 *             发送失败
	 * @throws MailAuthenticationException
	 *             发件箱认证失败
	 */
	public void sendEmail(SendBoxInfo sendBoxInfo, EmailContent sendContent, String[] to) throws MessagingException,
			MailException {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		// 设定mail server
		senderImpl.setHost(sendBoxInfo.getHost());
		senderImpl.setPort(sendBoxInfo.getPort());

		senderImpl.setUsername(sendBoxInfo.getUserName()); // 根据自己的情况,设置username
		senderImpl.setPassword(sendBoxInfo.getPwd()); // 根据自己的情况, 设置password
		senderImpl.setDefaultEncoding(sendBoxInfo.getDefaultEncoding());

		senderImpl.setJavaMailProperties(sendBoxInfo.getProperties());

		// 建立邮件消息
		MimeMessage mimeMessage = senderImpl.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		helper.setTo(to);
		helper.setFrom(sendBoxInfo.getFrom());
		helper.setSubject(sendContent.getSubject());
		helper.setText(sendContent.getContent(), sendContent.isHtml());

		// 发送邮件
		senderImpl.send(mimeMessage);

		logger.debug("邮件发送成功,email:{}", Arrays.toString(to));
	}
}
