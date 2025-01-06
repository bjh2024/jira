package com.mysite.jira.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private AccountService accountService;
	@Autowired
    private JiraService jiraService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private HttpSession session;

	// 로그인 성공시 호출 메서드
	// 로그인 성공시 jiraIdx 암호화 세션 저장(가장 최근에 방문한)
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, 
										HttpServletResponse response, 
										Authentication authentication) throws IOException, ServletException {
		// 현재 로그인된 계정
		Account account = accountService.getAccountByEmail(authentication.getName());
		Integer accountIdx = account.getIdx();
		
		// 현재 계정이 가장 최근 방문한 jira
		Jira jira = jiraService.getRecentTop1Jira(accountIdx);
		session.setAttribute("jiraIdx", jira.getIdx());
		
		// 현재 계정이 가장 최근 방문한 project
		Project project = projectService.getRecentTop1Project(accountIdx, jira.getIdx());
		String defaultUri = project == null ? "/" : "/project/"+ project.getKey() +"/summation";
		
		response.sendRedirect(defaultUri);
	}
}

