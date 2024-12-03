package com.mysite.jira.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	// 로그인 성공시 호출 메서드
	// 로그인 성공시 jiraIdx 암호화 세션 저장(가장 최근에 방문한)
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, 
										HttpServletResponse response, 
										Authentication authentication) throws IOException, ServletException{
		
		
		
	}
		
}
