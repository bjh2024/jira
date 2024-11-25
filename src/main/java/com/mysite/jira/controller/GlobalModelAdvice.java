package com.mysite.jira.controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mysite.jira.entity.Project;
import com.mysite.jira.service.BoardMainService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
@Component
public class GlobalModelAdvice {
	
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
