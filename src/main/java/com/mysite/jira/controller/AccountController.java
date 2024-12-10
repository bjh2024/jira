package com.mysite.jira.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mysite.jira.dto.account.AuthCodeForm;
import com.mysite.jira.dto.account.CreateUserForm;
import com.mysite.jira.email.EmailClient;
import com.mysite.jira.entity.Account;
import com.mysite.jira.service.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
	private final AccountService accountService;
	
	private final EmailClient emailClient;

	@GetMapping("/login")
	public String login() {
		return "account/login";
	}

	@GetMapping("/signup")
	public String signup(CreateUserForm createUserForm) {
		return "account/signup";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid CreateUserForm createUserForm, BindingResult bindingResult, 
			Authentication authentication, RedirectAttributes rttr) {
		
		if (bindingResult.hasErrors()) {
            return "account/signup";
        }
		if (!createUserForm.getPw().equals(createUserForm.getCheckpw())) {
            bindingResult.reject("checkpw",
                    "패스워드가 일치하지 않습니다.");
            return "account/signup";
        }
		
		try {
			Account user = accountService.singup(createUserForm.getUsername(), createUserForm.getEmail(), createUserForm.getPw());
			rttr.addFlashAttribute("user", user);
			emailClient.sendEmail(user.getEmail(), user.getAuthCode());
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
	public String checkAuthcode(AuthCodeForm authCodeForm) {
		return "account/check_authcode";
	}
	
	@PostMapping("/check_authcode")
	public String sendEmail(@Valid AuthCodeForm authCodeForm, BindingResult bindingResult,
			@ModelAttribute("user") Account user) {
		if (bindingResult.hasErrors()) {
            return "account/check_authcode";
        }
		if(authCodeForm.getEmail() == null) {
			bindingResult.reject("checkAuthcodeFailed", "이메일 값이 없습니다.");
            return "account/check_authcode";
		}
		String receivedCode = authCodeForm.getFirst() + authCodeForm.getSecond() + authCodeForm.getThird() + authCodeForm.getFourth();
		String usersCode = accountService.getAccountByEmail(authCodeForm.getEmail()).getAuthCode();
		System.out.println(receivedCode + "   " + usersCode);
		if(!receivedCode.equals(usersCode)) {
			bindingResult.reject("checkAuthcodeFailed", "입력한 코드가 전송된 코드와 다릅니다.");
            return "account/check_authcode";
		}else {
			accountService.updateAccount(authCodeForm.getEmail());
		}
		
		return "redirect:/account/login";
		// return "redirect:/account/login";
	}
	
	@GetMapping("/send_email")
	public String emailTest() {
		return "account/send_email";
	}
}
