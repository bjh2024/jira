package com.mysite.jira.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    private final TemplateEngine templateEngine;

    @Autowired
    public EmailService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildEmailContent(String authCode) {
        // Context 객체 생성 및 데이터 추가
        Context context = new Context();
        context.setVariable("authcode", authCode);

        // 템플릿 경로 지정 및 처리
        String templateName = "account/send_email";
        return templateEngine.process(templateName, context);
    }
    
    public String inviteBuildEmailContent(String to, Integer jiraIdx) {
        // Context 객체 생성 및 데이터 추가
        Context context = new Context();
        context.setVariable("userEmail", to);
        context.setVariable("jiraIdx", jiraIdx);
        // 템플릿 경로 지정 및 처리
        String templateName = "team/send_email";
        return templateEngine.process(templateName, context);
    }
}
