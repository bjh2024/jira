
package com.mysite.jira.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Filter;
import com.mysite.jira.entity.FilterAuth;
import com.mysite.jira.entity.FilterDone;
import com.mysite.jira.entity.FilterDoneDate;
import com.mysite.jira.entity.FilterIssueCreateDate;
import com.mysite.jira.entity.FilterIssuePriority;
import com.mysite.jira.entity.FilterIssueStatus;
import com.mysite.jira.entity.FilterIssueType;
import com.mysite.jira.entity.FilterIssueUpdate;
import com.mysite.jira.entity.FilterManager;
import com.mysite.jira.entity.FilterProject;
import com.mysite.jira.entity.FilterReporter;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.repository.AccountRepository;
import com.mysite.jira.repository.FilterAuthRepository;
import com.mysite.jira.repository.FilterDoneDateRepository;
import com.mysite.jira.repository.FilterDoneRepository;
import com.mysite.jira.repository.FilterIssueCreateDateRepository;
import com.mysite.jira.repository.FilterIssuePriorityRepository;
import com.mysite.jira.repository.FilterIssueStatusRepository;
import com.mysite.jira.repository.FilterIssueTypeRepository;
import com.mysite.jira.repository.FilterIssueUpdateRepository;
import com.mysite.jira.repository.FilterManagerRepository;
import com.mysite.jira.repository.FilterProjectRepository;
import com.mysite.jira.repository.FilterReporterRepository;
import com.mysite.jira.repository.FilterRepository;
import com.mysite.jira.repository.JiraRepository;

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
	private final FilterIssueUpdateRepository filterIssueUpdateRepository;
	private final FilterDoneDateRepository filterDoneDateRepository;
	private final FilterIssueCreateDateRepository filterIssueCreateDateRepository;
	private final FilterAuthRepository filterAuthRepository;
	private final AccountRepository accountRepository;
	private final JiraRepository jiraRepository;
	
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
		}
		return issueDoneIdxArr;
	}
	
	public FilterIssueUpdate getIssueUpdateDateByFilterIdx(Integer idx) {
		FilterIssueUpdate issue = filterIssueUpdateRepository.findByFilterIdx(idx);
		return issue;
	}
	
	public FilterIssueCreateDate getIssueCreateDateByFilterIdx(Integer idx) {
		FilterIssueCreateDate issue = filterIssueCreateDateRepository.findByFilterIdx(idx);
		return issue;
	}
	
	public FilterDoneDate getIssueDoneDateByFilterIdx(Integer idx) {
		FilterDoneDate issue = filterDoneDateRepository.findByFilterIdx(idx);
		return issue;
	}
	
	public List<FilterAuth> getFilterAuthAll(){
		return filterAuthRepository.findAll();
	}
	
	
	public Filter filterCreate(String name, String explain, Account account, Jira jira) {
		Filter filter = Filter.builder()
				.name(name)
				.explain(explain)
				.jira(jira)
				.account(account)
				.build();
		this.filterRepository.save(filter);
		return filter;
	}
	public void filterDoneCreate(Filter filter, Integer isCompleted) {
		FilterDone filterDone = FilterDone.builder()
				.filter(filter)
				.isCompleted(isCompleted)
				.build();
		this.filterDoneRepository.save(filterDone);
	}
	public void filterDoneDateCreate(Filter filter, LocalDateTime startDate, LocalDateTime endDate, Integer beforeDate) {
		FilterDoneDate filterDoneDate = FilterDoneDate.builder()
				.filter(filter)
				.startDate(startDate)
				.endDate(endDate)
				.beforeDate(beforeDate)
				.build();
		this.filterDoneDateRepository.save(filterDoneDate);
	}
}
