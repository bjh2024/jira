package com.mysite.jira.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mysite.jira.service.HeaderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	@GetMapping("/")
	public String myWorkPage(Model model) {
		return "my_work";
	}
	
	
	
	
	
	
}
