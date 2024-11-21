package com.mysite.jira;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {
	
	@GetMapping("/")
	public String filter() {
		return "my_work";
	}
	
	@GetMapping("/login")
	public String login() {
		return "/account/login";
	}
	
}

