package com.mysite.jira.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.mysite.jira.service.EmailService;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailClient {
	private final JavaMailSender mailSender;
	
	private final EmailService emailService;
	
	public void sendEmail(String to, String authCode) {
		try {
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			String subject = "[JIRA]: 인증 후 바로 JIRA를 이용하실 수 있습니다.";
			
			String emailContent = emailService.buildEmailContent(authCode);
			
			// log.debug("Generated email content: " + content);
			
			// 수신자, 제목, 본문 설정
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("kritac_jira@naver.com");
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(emailContent, true);
			 
			mailSender.send(mimeMessage);
		}catch (Exception e) {
		    log.error("Unexpected error occurred while sending email: ", e);
		}
	}
	
}
