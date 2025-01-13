package com.mysite.jira.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.entity.Account;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.IssueService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/issue")
public class IssueAPIController {
	
	private final AccountService accountService;
	
	private final IssueService issueService;
	
	private final HttpSession session;
	
	@GetMapping("allot/count")
	public Integer getAllotIssueCount(Principal principal) {
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		Account account = accountService.getAccountByEmail(principal.getName());
		Integer accountIdx = account.getIdx();
		return issueService.getMangerByIssueStatusInCount(jiraIdx, accountIdx);
	}
	
	
	
}
