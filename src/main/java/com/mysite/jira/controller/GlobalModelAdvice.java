package com.mysite.jira.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.Project;
import com.mysite.jira.service.BoardMainService;
import com.mysite.jira.service.HeaderService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
@Component
public class GlobalModelAdvice {

	private final HeaderService headerService;
	
	@ModelAttribute
	public void addHeaderAttributes(HttpServletRequest request, Model model) {
		String uri = request.getRequestURI(); 
		Integer accountIdx = 1;
		List<String> leaders = headerService.getjiraLeaderList(accountIdx);
		
		// 특정 경로 (/project/create)에서는 공통 모델 속성 추가하지 않기
		if (!uri.contains("/project/create")) {
			model.addAttribute("leaders", leaders);
		}
		
	}
	
	private final BoardMainService boardMainService;
	
	@ModelAttribute
	public void addProjectHeaderAttributes(HttpServletRequest request, Model model) {
		String uri = request.getRequestURI(); 
		Integer projectIdx = 1;
		Project project = boardMainService.getProjectNameById(projectIdx);
		
		if(uri.contains("/project")) {
			model.addAttribute("project", project);
		}
		
	}
	
}
