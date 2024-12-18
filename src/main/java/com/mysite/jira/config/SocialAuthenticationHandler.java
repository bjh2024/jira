package com.mysite.jira.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.JiraService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SocialAuthenticationHandler implements AuthenticationSuccessHandler {
	@Autowired
	private AccountService accountService;
	@Autowired
    private JiraService jiraService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
	    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
	    String email = (String) oAuth2User.getAttributes().get("email"); // OAuth2User에서 이메일 가져오기
	    System.out.println(email + "      dddd");
	    
	    Account account = accountService.getAccountByEmail(email); // 이메일로 Account 조회
	    Integer accountIdx = account.getIdx();
	    
	    Jira jira = jiraService.getRecentTop1Jira(accountIdx);
	    String jiraName = "";
	    String defaultUri = "";
	    if (jira == null) {
	    	jiraService.addJira(accountIdx);
	    	jiraName = jiraService.getRecentTop1Jira(accountIdx).getName();
	    	defaultUri = "/" + jiraName;
	    }else {
	    	jiraName = jira.getName();
	    }
	    
	    defaultUri = "/" + jiraName;
	    response.sendRedirect(defaultUri); // 리디렉션
	}
}
