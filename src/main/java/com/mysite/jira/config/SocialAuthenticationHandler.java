package com.mysite.jira.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.Project;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.JiraService;
import com.mysite.jira.service.ProjectService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class SocialAuthenticationHandler implements AuthenticationSuccessHandler {
	@Autowired
	private AccountService accountService;
	@Autowired
    private JiraService jiraService;
	@Autowired
	private HttpSession session;
	@Autowired
	private ProjectService projectService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
	    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
	    String id = oAuth2User.getAttributes().get("id").toString();
    	Account accKakao = accountService.getAccountByKakaoKey(id); // 인증키로 Account 조회
    	Account accNaver = accountService.getAccountByNaverKey(id);
    	Account account = accKakao != null ? accKakao : accNaver;
	    Integer accountIdx = account.getIdx();
	    
	    Jira jira = jiraService.getRecentTop1Jira(accountIdx);
	    session.setAttribute("jiraIdx", jira.getIdx());
	    
	    // 현재 계정이 가장 최근 방문한 project
		Project project = projectService.getRecentTop1Project(accountIdx, jira.getIdx());
		String defaultUri = project == null ? "/" : "/project/"+ project.getKey() +"/summation";
		
		response.sendRedirect(defaultUri);
	}
}
