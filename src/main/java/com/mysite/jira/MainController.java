package com.mysite.jira;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {

	@GetMapping("/")
	public String filter() {
		return "every_filter.html";
	}

	@GetMapping("/filter_issue")
	public String filterIssue() {
		return "filter_issue.html";
	}

	@GetMapping("/filter_issue_table")
	public String filterIssueTable() {
		return "filter_issue_table.html";
	}
	
	@GetMapping("/profile")
	public String profile() {
		return "profile.html";
	}
	
}
