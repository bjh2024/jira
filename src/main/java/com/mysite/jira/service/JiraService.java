package com.mysite.jira.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.JiraMembers;
import com.mysite.jira.repository.AccountRepository;
import com.mysite.jira.repository.JiraMembersRepository;
import com.mysite.jira.repository.JiraRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JiraService {
	
	private final AccountRepository accountRepository;
	
	private final JiraRepository jiraRepository;
	
	private final JiraMembersRepository jiraMembersRepository;
	
	public Optional<Jira> getByIdx(Integer idx) {
		return jiraRepository.findByIdx(idx);
	}
	 
	// kdw
	public List<String> getjiraLeaderList(Integer accountIdx) {
		return jiraRepository.findJiraAndMembersByAccountIdxName(accountIdx);
	}
	// 사용자가 포함하고 있는 지라의 개수
	public Integer getJiraCount(String jiraName, Integer accountIdx) {
		return jiraRepository.countByNameAndJiraMembersList_AccountIdx(jiraName, accountIdx);
	}
	// 사용자가 지라가 없을경우 지라 추가
	public void addJira(Integer accountIdx) {
		Optional<Account> account = accountRepository.findById(accountIdx);
		if(!account.isEmpty()) {
			String email = account.get().getEmail();
			Jira jira = Jira.builder()
				    		.name(email.substring(0, email.indexOf("@")))
				    		.account(account.get())
				    		.build();
			jiraRepository.save(jira);
			JiraMembers jiraMembers = JiraMembers.builder()
											     .jira(jira)
											     .account(account.get())
											     .clickedDate(LocalDateTime.now())
											     .build();
			jiraMembersRepository.save(jiraMembers);
		}
	}
	 
	public Jira getRecentTop1Jira(Integer accountIdx) {
		return jiraRepository.findByRecentClickedJira(accountIdx);
	}
	
	public Optional<Jira> getJiraByName(String name) {
		return jiraRepository.findByName(name);
	}
	
	public Jira getByNameJira(String name) {
		Optional<Jira> opJira = jiraRepository.findByName(name);
		if(!opJira.isEmpty()) {
			return opJira.get();
		}
		return null;
	}
}
