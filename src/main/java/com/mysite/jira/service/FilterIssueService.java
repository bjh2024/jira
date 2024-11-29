package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.jira.entity.Issue;
import com.mysite.jira.repository.IssueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilterIssueService {
	
	private final IssueRepository issueRepository;
 
	public List<Issue> getIssueByProjectIdxIn(String[] projectKey){
		List<Issue> result = issueRepository.findByProjectKeyIn(projectKey);
		return result;
	}
}
