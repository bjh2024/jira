package com.mysite.jira.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.Project;
import com.mysite.jira.service.IssueService;
import com.mysite.jira.service.ProjectService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/filter")
public class FilterController {
	
	private final IssueService issueService;
	private final ProjectService projectService;

	@GetMapping("/filter_issue")
	public String filterIssue(Model model) {
		Integer jiraIdx = 1;
		
		List<Issue> issue = issueService.getIssuesByJiraIdx(jiraIdx); 
		List<Project> project = projectService.getProjectByJiraIdx(jiraIdx);
		
		model.addAttribute("issue", issue);
		model.addAttribute("project", project);
		
		return "/filter/filter_issue.html";
	}
	
	@GetMapping("/every_filter")
	public String everyfilter() {
		return "/filter/every_filter.html";
	}

	@GetMapping("/filter_issue_table")
	public String filterIssueTable() {
		return "/filter/filter_issue_table.html";
	}
}
