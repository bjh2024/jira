package com.mysite.jira.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mysite.jira.service.ProjectService;
import com.mysite.jira.service.RecentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MyWorkController {
	
	private final ProjectService projectService;
	
	@GetMapping("/")
	public String filter(Model model) {
		Integer jiraIdx = 1;
		Integer accountIdx = 1;
		model.addAttribute("projectList", projectService.getProjectList(accountIdx, jiraIdx));
		return "my_work";
	}
	
}
