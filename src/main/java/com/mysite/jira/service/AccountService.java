package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;

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
	
	public Account singup(String username, String email, String pw) {
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
		return null;
	}
	
}