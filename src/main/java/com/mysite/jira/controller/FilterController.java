package com.mysite.jira.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/filter")
public class FilterController {

	@GetMapping("/filter_issue")
	public String filterIssue() {
		return "filter_issue.html";
	}

	@GetMapping("/filter_issue_table")
	public String filterIssueTable() {
		return "filter_issue_table.html";
	}
}
