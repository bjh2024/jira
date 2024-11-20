package com.mysite.jira;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String filter() {
		return "my_work";
	}
	
}

