package com.mysite.jira;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.JiraMembers;
import com.mysite.jira.entity.JiraRecentClicked;
import com.mysite.jira.repository.AccountRepository;
import com.mysite.jira.repository.JiraMembersRepository;
import com.mysite.jira.repository.JiraRecentClickedRepository;
import com.mysite.jira.repository.JiraRepository;

@SpringBootTest
class JiraApplicationTests {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private JiraRepository jiraRepository;
	
	@Autowired
	private JiraMembersRepository jiraMembersRepository;
	
	@Autowired
	private JiraRecentClickedRepository jiraRecentClickedRepository;
	
	@Test
	@Transactional
	void test() {
		// jiraMember 테이블
		List<Account> accountList = accountRepository.findAll();
		List<Jira> jiraList = jiraRepository.findAll();
		for(int i = 0; i < 300; i++) {
			int jiraRn = (int)(Math.random()*jiraList.size());
			
			Account account = accountList.get(i);
			Jira jira = jiraList.get(jiraRn);
			
			JiraMembers jiraMembers = JiraMembers.builder()
											     .account(account)
											     .jira(jira)
											     .build();
			jiraMembersRepository.save(jiraMembers);
			
			JiraRecentClicked jiraRecentClicked = JiraRecentClicked.builder()
																   .jira(jira)
																   .account(account)
																   .build();
			jiraRecentClickedRepository.save(jiraRecentClicked);
		}
	}
}

