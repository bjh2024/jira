package com.mysite.jira.service;

import org.springframework.stereotype.Service;

import com.mysite.jira.repository.IssueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilterIssueService {
	
	private final IssueRepository issueRepository;
 
}
