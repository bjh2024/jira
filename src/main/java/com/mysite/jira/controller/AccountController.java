package com.mysite.jira.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.jira.dto.account.CreateUserForm;
import com.mysite.jira.service.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
	private final AccountService accountService;

	@GetMapping("/login")
	public String login() {
		return "account/login";
	}

	@GetMapping("/signup")
	public String signup(CreateUserForm createUserForm) {
		return "account/signup";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid CreateUserForm createUserForm, BindingResult bindingResult, Authentication authentication) {
		System.out.println(createUserForm.getUsername());
		
		if (bindingResult.hasErrors()) {
            return "account/signup";
        }
		if (!createUserForm.getPw().equals(createUserForm.getCheckpw())) {
            bindingResult.rejectValue("passwordInCorrect", 
                    "패스워드가 일치하지 않습니다.");
            return "account/signup";
        }
		
		try {
			accountService.singup(createUserForm.getUsername(), createUserForm.getEmail(), createUserForm.getPw());
		}catch(DataIntegrityViolationException e) {
			e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "account/signup";
		}catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "account/signup";
        }
		
		return "redirect:/account/check_authcode";
	}
	
	@GetMapping("/check_authcode")
	public String checkAuthcode() {
		return "account/check_authcode";
	}
}
