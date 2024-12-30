	package com.mysite.jira.controller;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

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
@SessionAttributes("user")
public class AccountController {
	private final AccountService accountService;
	
	private final EmailClient emailClient;
	
	@GetMapping("/login")
	public String login(Model model) {
		return "account/login";
	}
	
//	@GetMapping("/login/naver")
//    public CustomResponseEntity<UserResponse.Login> loginByNaver(@RequestParam(name = "code") String code) {
//        return CustomResponseEntity.success(userService.loginByOAuth(code, NAVER));
//    }

	@GetMapping("/signup")
	public String signup(CreateUserForm createUserForm) {
		return "account/signup";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid CreateUserForm createUserForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
            return "account/signup";
        }
		if (!createUserForm.getPw().equals(createUserForm.getCheckpw())) {
            bindingResult.rejectValue("checkpw", "error.createUserForm", "패스워드가 일치하지 않습니다.");
            return "account/signup";
        }
		
		try {
			Account user = accountService.signup(createUserForm.getUsername(), createUserForm.getEmail(), createUserForm.getPw());
			model.addAttribute("user", user);
			emailClient.sendEmail(user.getEmail(), user.getAuthCode());
		}catch(DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.rejectValue("email", "error.createUserForm", "이미 등록된 사용자입니다.");
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
	public String sendEmail(@Valid AuthCodeForm authCodeForm, BindingResult bindingResult) {
		if(authCodeForm.getEmail() == null) {
			bindingResult.reject("checkAuthcodeFailed", "이메일 값이 없습니다.");
            return "account/check_authcode";
		}
		
		Account account = accountService.getAccountByEmail(authCodeForm.getEmail());
		
		String receivedCode = authCodeForm.getFirst() + authCodeForm.getSecond() + authCodeForm.getThird() + authCodeForm.getFourth();
		String usersCode = account.getAuthCode();
		System.out.println(receivedCode + "   " + usersCode);
		LocalDateTime currentTime  = LocalDateTime.now();
		if(!receivedCode.equals(usersCode)) {
			bindingResult.rejectValue("email", "error.authCodeForm", "입력한 코드가 전송된 코드와 다릅니다.");
            return "account/check_authcode";
		}else if(currentTime.isAfter(account.getCodeExpDate())){
			bindingResult.rejectValue("email", "error.authCodeForm", "인증 코드가 만료되었습니다. 다시 시도해 주세요.");
            return "account/check_authcode";
		}else{
			accountService.updateAccount(authCodeForm.getEmail());
		}
		
		return "redirect:/account/login";
	}

	@GetMapping("/reset_code")
	public String resetCode(@RequestParam("email") String email, AuthCodeForm authCodeForm, Model model) {
		Account user = accountService.getAccountByEmail(email);
		Account updatedUser = accountService.resetCode(user);
		
		emailClient.sendEmail(updatedUser.getEmail(), updatedUser.getAuthCode());
		model.addAttribute("user", updatedUser);
		
		return "redirect:/account/check_authcode";
	}
	
	
}
