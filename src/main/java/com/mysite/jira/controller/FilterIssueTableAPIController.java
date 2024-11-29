package com.mysite.jira.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.entity.Issue;
import com.mysite.jira.repository.IssueRepository;
import com.mysite.jira.service.FilterIssueService;
import com.mysite.jira.service.IssueService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/filter_issue_table")
public class FilterIssueTableAPIController {

	private final FilterIssueService filterIssueService; 
	
	@PostMapping("/project_filter")
	public List<Issue> getInputDatas(@RequestBody String[] projectIdx){
		List<Issue> issueByProjectIdx = filterIssueService.getIssueByProjectIdxIn(projectIdx);
		return issueByProjectIdx;
	}
}
