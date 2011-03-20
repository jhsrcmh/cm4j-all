package com.cm4j.email.sender;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	 */
	public void sendEmail(SendBoxInfo sendBoxInfo, EmailContent sendContent, String[] to) throws MessagingException {
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
		// 设置收件人，寄件人 用数组发送多个邮件
		// String[] array = new String[] {"sun111@163.com","sun222@sohu.com"};
		// mailMessage.setTo(array);
		helper.setTo(to);
		helper.setFrom(sendBoxInfo.getFrom());
		helper.setSubject(sendContent.getSubject());
		helper.setText(sendContent.getContent(), sendContent.isHtml());

		// 发送邮件
		senderImpl.send(mimeMessage);

		logger.debug("邮件发送成功,email:{}", to);
	}

	public static void main(String[] args) throws MessagingException {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		// 设定mail server
		senderImpl.setHost("smtp.163.com");
		senderImpl.setPort(25);

		senderImpl.setUsername("syniii"); // 根据自己的情况,设置username
		senderImpl.setPassword("930905"); // 根据自己的情况, 设置password
		senderImpl.setDefaultEncoding("UTF-8");

		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "false"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(prop);

		// 建立邮件消息
		MimeMessage mimeMessage = senderImpl.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		// 设置收件人，寄件人 用数组发送多个邮件
		// String[] array = new String[] {"sun111@163.com","sun222@sohu.com"};
		// mailMessage.setTo(array);
		helper.setTo("syniii@sogou.com");
		helper.setFrom("syniii@163.com");
		helper.setSubject(" 简单文本邮件发送！ ");
		helper.setText("测试我的<font color=red>简单邮件</font>发送机制！！", true);

		// 发送邮件
		senderImpl.send(mimeMessage);

		System.out.println(" 邮件发送成功.. ");
	}

}
