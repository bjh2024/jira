package com.mysite.jira.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.mysite.jira.entity.Issue;
import com.mysite.jira.repository.IssueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {

	private final IssueRepository issueRepository;
	
	public List<Issue> getIssuesByJiraIdx(Integer jiraIdx){
		return issueRepository.findByJiraIdx(jiraIdx);
	}
	
}
