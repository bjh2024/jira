package com.mysite.jira.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.JiraMembers;
import com.mysite.jira.entity.JiraRecentClicked;
import com.mysite.jira.repository.AccountRepository;
import com.mysite.jira.repository.JiraMembersRepository;
import com.mysite.jira.repository.JiraRecentClickedRepository;
import com.mysite.jira.repository.JiraRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JiraService {
	
	private final AccountRepository accountRepository;
	private final JiraRepository jiraRepository;
	private final JiraMembersRepository jiraMembersRepository;
	
	private final JiraRecentClickedRepository jiraRecentClickedRepository;
	
	private final FilterService filterService;
	
	public Optional<Jira> getIdxByName(String name){
		return jiraRepository.findIdxByName(name);
	}
	
	public Jira getByIdx(Integer idx) {
		Optional<Jira> opJira = jiraRepository.findByIdx(idx);
		Jira jira = null;
		if(!opJira.isEmpty()) {
			jira = opJira.get();
		}
		return jira;
	}
	 
	// kdw
	public List<JiraMembers> getJiraByAccountIdxList(Integer accountIdx) {
		return jiraMembersRepository.findByAccountIdx(accountIdx);
	}
	// 사용자가 포함하고 있는 지라의 개수
	public Integer getJiraCount(String jiraName, Integer accountIdx) {
		return jiraRepository.countByNameAndJiraMembersList_AccountIdx(jiraName, accountIdx);
	}
	
	public void addJiraRecentClicked(Jira jira, Account account) {
		JiraRecentClicked jiraRecentClicked = jiraRecentClickedRepository.findByJira_idxAndAccount_idx(jira.getIdx(), account.getIdx());
		if(jiraRecentClicked != null) {
			jiraRecentClicked.updateDate();
			return;
		}
		jiraRecentClicked = JiraRecentClicked.builder()
											 .jira(jira)
											 .account(account)
											 .build();
		jiraRecentClickedRepository.save(jiraRecentClicked);
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
			
			this.addJiraRecentClicked(jira ,account.get());
			// 기본값 필터 add
			filterService.defaultMyPendingIssues(jira, account.get());
			filterService.defaultReporter(jira, account.get());
			filterService.defaultAllIssue(jira, account.get());
			filterService.defaultPendingIssue(jira, account.get());
			filterService.defaultDoneIssue(jira, account.get());
			filterService.defaultRecentlyCreated(jira, account.get());
			filterService.defaultRecentlyDone(jira, account.get());
			filterService.defaultRecentlyUpdate(jira, account.get());
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
