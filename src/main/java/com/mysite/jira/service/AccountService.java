package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.JiraMembers;
import com.mysite.jira.repository.JiraMembersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final JiraMembersRepository jiraMembersRepository;
	
	public List<Account> getAccountList(Integer jiraIdx) {
		List<JiraMembers> jiraMembers = jiraMembersRepository.findByJiraIdx(jiraIdx);

		List<Account> result = new ArrayList<>();
		for (int i = 0; i < jiraMembers.size(); i++) {
			result.add(jiraMembers.get(i).getAccount());
		}
		return result;
	}
	
}
