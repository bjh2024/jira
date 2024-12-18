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
public class JiraInviteEmailClient {
	
	private final JavaMailSender mailSender;
	
	private final EmailService emailService;
	
	public void sendEmail(String to, String uri) {
		try {
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			String subject = "[JIRA]👋관리자가 당신을 Jira에 초대했습니다.";
			String emailContent = emailService.inviteBuildEmailContent(uri);
			
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("dahyunkid@naver.com");
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(emailContent, true);
			
			mailSender.send(mimeMessage);
		}catch(Exception e) {
			log.error("Unexpected error occurred while sending email: ", e);
		}
	}
	
}
