package com.mysite.jira.controller;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.jira.dto.IssueTypeListDto;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.JiraMembers;
import com.mysite.jira.entity.Project;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.IssueService;
import com.mysite.jira.service.IssueTypeService;
import com.mysite.jira.service.ProjectService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/filter")
public class FilterController {

	private final IssueService issueService;
	private final ProjectService projectService;
	private final IssueTypeService issueTypeService;
	private final AccountService accountService;

	@GetMapping("/filter_issue")
	public String filterIssue(Model model) {
		Integer jiraIdx = 1;

		List<Issue> issue = issueService.getIssuesByJiraIdx(jiraIdx);
		model.addAttribute("issue", issue);

		List<Project> project = projectService.getProjectByJiraIdx(jiraIdx);
		model.addAttribute("project", project);

		List<IssueTypeListDto> issueType = issueTypeService.getDistinctIssueTypes(jiraIdx);
		model.addAttribute("issueType", issueType);

		List<Object[]> issueStatus = projectService.getDistinctStatusAndNameByJiraIdx(jiraIdx);
		model.addAttribute("issueStatus", issueStatus);


		return "/filter/filter_issue.html";
	}

	@GetMapping("/every_filter")
	public String everyfilter() {
		return "/filter/every_filter.html";
	}

	@GetMapping("/filter_issue_table")
	public String filterIssueTable(Model model) {
		Integer jiraIdx = 1;

		List<Issue> issue = issueService.getIssuesByJiraIdx(jiraIdx);
		model.addAttribute("issue", issue);

		List<Project> project = projectService.getProjectByJiraIdx(jiraIdx);
		model.addAttribute("project", project);

		List<IssueTypeListDto> issueType = issueTypeService.getDistinctIssueTypes(jiraIdx);
		model.addAttribute("issueType", issueType);

		List<Object[]> issueStatus = projectService.getDistinctStatusAndNameByJiraIdx(jiraIdx);
		model.addAttribute("issueStatus", issueStatus);

		return "/filter/filter_issue_table.html";
	}
}
