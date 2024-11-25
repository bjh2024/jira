package com.mysite.jira.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

	@GetMapping("/login")
	public String login() {
		return "account/login";
	}

	@GetMapping("/signup")
	public String signin() {
		return "account/signup";
	}

	@GetMapping("/check_authcode")
	public String checkAuthcode() {
		return "account/check_authcode";
	}
	
	@GetMapping("/profile")
	public String profile() {
		return "account/profile.html";
	}

}
