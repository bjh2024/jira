package com.mysite.jira.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project")
public class ProjectController {
	
	@GetMapping("/summation")
	public String summationPage() {
		return "project/summation";
	}
	
	@GetMapping("/list")
	public String listPage() {
		return "project/project_list";
	}
	
	@GetMapping("/create")
	public String createPage() {
		return "project/project_create";
	}
	
	@GetMapping("/board_main")
	public String root() {
		return "project/board_main";
	}
	
	@GetMapping("/setting_issue_type")
	public String settingIssueType() {
		return "project/setting_issue_type";
	}
	
	@GetMapping("/attached_files")
	public String attachedFiles() {
		return "project/attached_files";
	}
}
