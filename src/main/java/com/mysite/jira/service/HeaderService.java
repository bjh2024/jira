package com.mysite.jira.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.jira.repository.IssueRepository;
import com.mysite.jira.repository.JiraRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HeaderService {

	private final JiraRepository jiraRepository;
	
	private final IssueRepository issueRepository;
	
	// jira리더 list
	public List<String> getjiraLeaderList(Integer accountIdx){
		List<String> result = jiraRepository.findJiraAndMembersByAccountIdxName(accountIdx);
		// 두글자만 나오게
		for(int i = 0; i < result.size(); i++) {
			result.set(i, result.get(i).substring(0, 2));
		}
		return result;
	}
	
	
}
