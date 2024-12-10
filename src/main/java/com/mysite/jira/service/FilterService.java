package com.mysite.jira.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.jira.entity.Filter;
import com.mysite.jira.repository.FilterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilterService {

	private final FilterRepository filterRepository;
	
	public List<Filter> getByAccountIdxAndJiraIdx(Integer accountIdx, Integer jiraIdx){
		return filterRepository.findByAccountIdxAndJiraIdx(accountIdx, jiraIdx);
	}
	public List<Filter> getByJiraIdx(Integer idx){
		System.out.println(filterRepository.findByJiraIdx(idx).size());
		return filterRepository.findByJiraIdx(idx);
	}
}
