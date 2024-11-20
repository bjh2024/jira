package com.mysite.jira;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping("/test")
	public String test() {
		return "test";
	}
	
	@GetMapping("/project/board_main")
	public String root() {
		return "/project/board_main";
	}
	
	@GetMapping("/project/summation")
	public String summation() {
		return "/project/summation";
	}
	
	@GetMapping("/project/setting_issue_type")
	public String settingIssueType() {
		return "/project/setting_issue_type";
	}
	
	@GetMapping("/project/attached_files")
	public String attachedFiles() {
		return "/project/attached_files";
	}
	
	@GetMapping("/login/login")
	public String login() {
		return "/login/login";
	}
	
	@GetMapping("/login/signup")
	public String signin() {
		return "/login/signup";
	}
	
	@GetMapping("/login/check_authcode")
	public String checkAuthcode() {
		return "/login/check_authcode";
	}
}
