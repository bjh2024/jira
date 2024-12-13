package com.mysite.jira.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.IssueService;
import com.mysite.jira.service.JiraService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/issue")
public class IssueAPIController {
	
	private final AccountService accountService;
	
	private final JiraService jiraService;
	
	private final IssueService issueService;
	
	@GetMapping("allot/count")
	public Integer getAllotIssueCount(@RequestParam("uri") String uri, Principal principal) {
		Account account = accountService.getAccountByEmail(principal.getName());
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		return issueService.getMangerByIssueStatusInCount(jira.getIdx(), account.getIdx());
	}
	
	
	
}
