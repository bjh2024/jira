package com.mysite.jira.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.JiraMembers;
import com.mysite.jira.repository.AccountRepository;
import com.mysite.jira.repository.JiraMembersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final JiraMembersRepository jiraMembersRepository;
	
	private final AccountRepository accountRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	public List<JiraMembers> getByJiraIdx(Integer idx){
		return jiraMembersRepository.findByJiraIdx(idx);
	}
	
	public List<Account> getAccountList(Integer jiraIdx) {
		List<JiraMembers> jiraMembers = jiraMembersRepository.findByJiraIdx(jiraIdx);

		List<Account> result = new ArrayList<>();
		for (int i = 0; i < jiraMembers.size(); i++) {
			result.add(jiraMembers.get(i).getAccount());
		}
		return result;
	}
	
	public Account signup(String username, String email, String pw) {
		String code = "";
		for(int i = 0; i < 4; i++) {
			int tempCode = (int) (Math.random() * 26) + 65;
			code += (char)tempCode;
		}
		Account newUser = Account.builder()
								.name(username)
								.email(email)
								.pw(passwordEncoder.encode(pw))
								.iconFilename("user_icon_file5.png")
								.authCode(code)
								.build();
		this.accountRepository.save(newUser);
		return newUser;
	}
	
	public Account getAccountByEmail(String email) {
		Optional<Account> optAccount = this.accountRepository.findByEmail(email);
		Account account = null;
		if(optAccount.isPresent()) {
			account = optAccount.get();
		}else {
			throw new NoSuchElementException("Account not found");
		}
		return account;
	}
	
	public void updateAccount(String email) {
		Optional<Account> optUser = this.accountRepository.findByEmail(email);
		Account existingAccount = null;
		if(optUser.isPresent()) {
			existingAccount = optUser.get();
		}
		if(existingAccount == null) {
			throw new NoSuchElementException("Account not found");
		}
		
		existingAccount.updateAccount(null, null);
		this.accountRepository.save(existingAccount);
	}
	
	public Account resetCode(Account user) {
		String code = "";
		for(int i = 0; i < 4; i++) {
			int tempCode = (int) (Math.random() * 26) + 65;
			code += (char)tempCode;
		}
		user.updateAccount(code, LocalDateTime.now().plusMinutes(3));
		this.accountRepository.save(user);
		return user;
	}
	
	public Account getAccountByKakaoKey(String key){
		Optional<Account> account = this.accountRepository.findByKakaoSocialKey(key);
		return account.get();
	}
	
}
