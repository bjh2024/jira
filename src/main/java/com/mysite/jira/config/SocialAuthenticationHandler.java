package com.mysite.jira.config;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
	    String id = oAuth2User.getAttributes().get("id").toString();
    	Account accKakao = accountService.getAccountByKakaoKey(id); // 인증키로 Account 조회
    	Account accNaver = accountService.getAccountByNaverKey(id);
    	Account account = accKakao != null ? accKakao : accNaver;
	    Integer accountIdx = account.getIdx();
	    
	    List<Jira> jiraList = jiraService.getRecentTop1Jira(accountIdx);
	    Jira jira = jiraList.get(0);
	    String jiraName = "";
	    String defaultUri = "";
	    if (jira == null) {
	    	jiraService.addJira(accountIdx);
	    	jiraName = jiraList.get(0).getName();
	    	defaultUri = "/" + jiraName;
	    }else {
	    	jiraName = jira.getName();
	    }
	    
	    defaultUri = "/" + jiraName;
	    response.sendRedirect(defaultUri); // 리디렉션
	}
}
