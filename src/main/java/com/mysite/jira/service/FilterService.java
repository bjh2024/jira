package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.jira.entity.Filter;
import com.mysite.jira.entity.FilterProject;
import com.mysite.jira.repository.FilterProjectRepository;
import com.mysite.jira.repository.FilterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilterService {

	private final FilterRepository filterRepository;
	private final FilterProjectRepository filterProjectRepository;
	
	public List<Filter> getByAccountIdxAndJiraIdx(Integer accountIdx, Integer jiraIdx){
		return filterRepository.findByAccountIdxAndJiraIdx(accountIdx, jiraIdx);
	}
	public List<Filter> getByJiraIdx(Integer idx){
		System.out.println(filterRepository.findByJiraIdx(idx).size());
		return filterRepository.findByJiraIdx(idx);
	}
	public List<Integer> getByFilterIdx(Integer idx){
		List<FilterProject> filterProject = filterProjectRepository.findByFilterIdx(idx);
		System.out.println("gdgd");
		List<Integer> projectIdxArr = new ArrayList<>();
		for (int i = 0; i < filterProject.size(); i++) {
			projectIdxArr.add(filterProject.get(i).getProject().getIdx());
			}
		return projectIdxArr;
	}
}
