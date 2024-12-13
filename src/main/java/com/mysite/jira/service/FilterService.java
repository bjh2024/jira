package com.mysite.jira.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.jira.entity.Filter;
import com.mysite.jira.entity.FilterDone;
import com.mysite.jira.entity.FilterDoneDate;
import com.mysite.jira.entity.FilterIssuePriority;
import com.mysite.jira.entity.FilterIssueStatus;
import com.mysite.jira.entity.FilterIssueType;
import com.mysite.jira.entity.FilterManager;
import com.mysite.jira.entity.FilterProject;
import com.mysite.jira.entity.FilterReporter;
import com.mysite.jira.repository.FilterDoneDateRepository;
import com.mysite.jira.repository.FilterDoneRepository;
import com.mysite.jira.repository.FilterIssuePriorityRepository;
import com.mysite.jira.repository.FilterIssueStatusRepository;
import com.mysite.jira.repository.FilterIssueTypeRepository;
import com.mysite.jira.repository.FilterManagerRepository;
import com.mysite.jira.repository.FilterProjectRepository;
import com.mysite.jira.repository.FilterReporterRepository;
import com.mysite.jira.repository.FilterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilterService {

	private final FilterRepository filterRepository;
	private final FilterProjectRepository filterProjectRepository;
	private final FilterIssueTypeRepository filterIssueTypeRepository;
	private final FilterIssueStatusRepository filterIssueStatusRepository;
	private final FilterManagerRepository filterManagerRepository;
	private final FilterIssuePriorityRepository filterIssuePriorityRepository;
	private final FilterReporterRepository filterReporterRepostiory;
	private final FilterDoneRepository filterDoneRepository;
	private final FilterDoneDateRepository filterDoneDateRepository;
	
	public List<Filter> getByAccountIdxAndJiraIdx(Integer accountIdx, Integer jiraIdx){
		return filterRepository.findByAccountIdxAndJiraIdx(accountIdx, jiraIdx);
	}
	public List<Filter> getByJiraIdx(Integer idx){
		System.out.println(filterRepository.findByJiraIdx(idx).size());
		return filterRepository.findByJiraIdx(idx);
	}
	public List<Integer> getProjectIdxArrByFilterIdx(Integer idx){
		List<FilterProject> filterProject = filterProjectRepository.findByFilterIdx(idx);
		List<Integer> projectIdxArr = new ArrayList<>();
		for (int i = 0; i < filterProject.size(); i++) {
			projectIdxArr.add(filterProject.get(i).getProject().getIdx());
			}
		return projectIdxArr;
	}
	
	public List<String> getIssueTypeNameArrByFilterIdx(Integer idx){
		List<FilterIssueType> filterIssueType = filterIssueTypeRepository.findByFilterIdx(idx);
		List<String> issueTypeNameArr = new ArrayList<>();
		for (int i = 0; i < filterIssueType.size(); i++) {
			issueTypeNameArr.add(filterIssueType.get(i).getIssueType().getName());
		}
		return issueTypeNameArr;
	}
	
	public List<String> getIssueStatusNameArrByFilterIdx(Integer idx){
		List<FilterIssueStatus> filterIssueStatus = filterIssueStatusRepository.findByFilterIdx(idx);
		List<String> issueStatusNameArr = new ArrayList<>();
		for (int i = 0; i < filterIssueStatus.size(); i++) {
			issueStatusNameArr.add(filterIssueStatus.get(i).getIssueStatus().getName());
		}
		return issueStatusNameArr;
	}
	
	public List<String> getIssueManagerNameArrByFilterIdx(Integer idx){
		List<FilterManager> filterManager = filterManagerRepository.findByFilterIdx(idx);
		List<String> issueManagerNameArr = new ArrayList<>();
		for (int i = 0; i < filterManager.size(); i++) {
			issueManagerNameArr.add(filterManager.get(i).getAccount() == null ?
					"할당되지 않음":filterManager.get(i).getAccount().getName());
		}
		return issueManagerNameArr;
	}
	
	public List<Integer> getIssuePriorityIdxByFilterIdx(Integer idx){
		List<FilterIssuePriority> filterIssuePriority = filterIssuePriorityRepository.findByFilterIdx(idx);
		List<Integer> issuePriorityIdxArr = new ArrayList<>();
		for (int i = 0; i < filterIssuePriority.size(); i++) {
			issuePriorityIdxArr.add(filterIssuePriority.get(i).getIssuePriority().getIdx());
		}
		return issuePriorityIdxArr;
	}
	
	public List<String> getIssueReporterNameArrByFilterIdx(Integer idx){
		List<FilterReporter> filterReporter = filterReporterRepostiory.findByFilterIdx(idx);
		List<String> filterReporterNameArr = new ArrayList<>();
		for(int i = 0; i < filterReporter.size(); i++) {
			filterReporterNameArr.add(filterReporter.get(i).getAccount().getName());
		}
		return filterReporterNameArr;
	}
	
	public List<Integer> getIssueStatusIdxByFilterIdx(Integer idx){
		List<FilterDone> issueDone = filterDoneRepository.findByFilterIdx(idx);
		List<Integer> issueDoneIdxArr = new ArrayList<>();
		
		for (int i = 0; i < issueDone.size(); i++) {
			issueDoneIdxArr.add(issueDone.get(i).getIsCompleted());
			System.out.println("둘다 나오냐?"+issueDone.get(i).getIsCompleted());
		}
		return issueDoneIdxArr;
	}
	
	public Integer getIssueDoneDateByFilterIdx(Integer idx) {
		return 1;
	}
}
