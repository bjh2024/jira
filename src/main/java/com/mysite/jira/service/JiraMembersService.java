package com.mysite.jira.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.JiraMembers;
import com.mysite.jira.repository.JiraMembersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JiraMembersService {

	private final JiraMembersRepository jiraMembersRepository;
	
	public List<JiraMembers> getMembersByJiraIdx(Integer idx){
		return jiraMembersRepository.findByJiraIdx(idx); 
	}
	
	public JiraMembers getByJiraIdxAndEmailJiraMember(Integer jiraIdx, String email) {
		return jiraMembersRepository.findByJiraIdxAndAccount_Email(jiraIdx, email);
	}
	
	public void addJiraMember(Account account, Jira jira) {
		JiraMembers jiraMembers = JiraMembers.builder()
											 .jira(jira)
											 .account(account)
											 .build();
		
		jiraMembersRepository.save(jiraMembers);
	}
}
