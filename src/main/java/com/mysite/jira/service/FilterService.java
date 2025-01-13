
package com.mysite.jira.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.jira.dto.LikeContentDTO;
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
import com.mysite.jira.entity.FilterLikeMembers;
import com.mysite.jira.entity.FilterManager;
import com.mysite.jira.entity.FilterProject;
import com.mysite.jira.entity.FilterRecentClicked;
import com.mysite.jira.entity.FilterReporter;
import com.mysite.jira.entity.IssuePriority;
import com.mysite.jira.entity.IssueStatus;
import com.mysite.jira.entity.IssueType;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.Project;
import com.mysite.jira.entity.Team;
import com.mysite.jira.repository.AccountRepository;
import com.mysite.jira.repository.FilterAuthRepository;
import com.mysite.jira.repository.FilterDoneDateRepository;
import com.mysite.jira.repository.FilterDoneRepository;
import com.mysite.jira.repository.FilterIssueCreateDateRepository;
import com.mysite.jira.repository.FilterIssuePriorityRepository;
import com.mysite.jira.repository.FilterIssueStatusRepository;
import com.mysite.jira.repository.FilterIssueTypeRepository;
import com.mysite.jira.repository.FilterIssueUpdateRepository;
import com.mysite.jira.repository.FilterLikeMembersRepository;
import com.mysite.jira.repository.FilterManagerRepository;
import com.mysite.jira.repository.FilterProjectRepository;
import com.mysite.jira.repository.FilterRecentClickedRepository;
import com.mysite.jira.repository.FilterReporterRepository;
import com.mysite.jira.repository.FilterRepository;
import com.mysite.jira.repository.JiraRepository;

import jakarta.servlet.http.HttpSession;
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
	private final FilterLikeMembersRepository filterLikeMembersRepository;
	private final FilterRecentClickedRepository filterRecentClickedRepository;
	private final AccountRepository accountRepository;
	private final JiraRepository jiraRepository;
	private final HttpSession session;
	
	public FilterLikeMembers getByAccountIdxAndFilterIdx(Integer accountIdx, Integer filterIdx) {
		return filterLikeMembersRepository.findByAccountIdxAndFilterIdx(accountIdx, filterIdx);
	}
	public Filter getByIdx(Integer idx) {
		return filterRepository.findById(idx).get();
	}
	public List<FilterLikeMembers> getByAccountIdx(Integer accountIdx){
		return filterLikeMembersRepository.findByAccountIdx(accountIdx);
	}
	public List<Filter> getByIdxIn(Integer[] idx){
		return filterRepository.findByIdxIn(idx);
	}
	public List<Filter> getAll(){
		return filterRepository.findAll();
	}
	public List<Filter> getByAccountIdxAndJiraIdx(Integer accountIdx, Integer jiraIdx){
		return filterRepository.findByAccountIdxAndJiraIdxOrderByIdxAsc(accountIdx, jiraIdx);
	}
	
	public List<Filter> getByAccountIdxAndJiraIdxMinusLikeAndRecent(Integer accountIdx, Integer jiraIdx, List<Filter> filterRecentList, List<LikeContentDTO> filterLikeMembers){
		List<Filter> filterList = filterRepository.findByAccountIdxAndJiraIdxOrderByIdxAsc(accountIdx, jiraIdx);
		Integer[] filterLikeInteger = new Integer[filterLikeMembers.size()];
		for (int i = 0; i < filterLikeInteger.length; i++) {
			filterLikeInteger[i] = filterLikeMembers.get(i).getIdx();
		}
		List<Filter> filterLikes = this.getByIdxIn(filterLikeInteger);
		List<Filter> defaultFilterList = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			defaultFilterList.add(filterList.get(i));
		}
		defaultFilterList.removeAll(filterRecentList);
		defaultFilterList.removeAll(filterLikes);
		return defaultFilterList;
	}
	
	public List<Filter> getByJiraIdx(Integer jiraIdx){
		return filterRepository.findByJiraIdx(jiraIdx);
	}
	public List<Filter> getByJiraIdx_AccountIdx(Integer jiraIdx,Integer accountIdx){
		return filterRepository.findByJiraIdxAndAccountIdxOrderByIdxAsc(jiraIdx,accountIdx);
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
// 기본 필터 생성 기능 --------------------------------------------------------------------------------------------
	public void defaultMyPendingIssues(Jira jira,Account account) {
		Filter filter = Filter.builder()
				.name("나의 미해결 이슈")
				.explain(null)
				.jira(jira)
				.account(account)
				.build();
		this.filterRepository.save(filter);
		FilterManager filterManager = FilterManager.builder()
				.filter(filter)
				.account(account)
				.build();
		this.filterManagerRepository.save(filterManager);
		FilterDone filterDone = FilterDone.builder()
				.filter(filter)
				.isCompleted(0)
				.build();
		this.filterDoneRepository.save(filterDone);
		
	}
	
	public void defaultReporter(Jira jira, Account account) {
		Filter filter = Filter.builder()
				.name("내가 보고함")
				.explain(null)
				.jira(jira)
				.account(account)
				.build();
		this.filterRepository.save(filter);
		FilterReporter filterReporter = FilterReporter.builder()
				.filter(filter)
				.account(account)
				.build();
		this.filterReporterRepostiory.save(filterReporter);
		
	}
	public void defaultAllIssue(Jira jira, Account account) {
		Filter filter = Filter.builder()
				.name("모든 이슈")
				.jira(jira)
				.explain(null)
				.account(account)
				.build();
		this.filterRepository.save(filter);
	}
	public void defaultPendingIssue(Jira jira, Account account) {
		Filter filter = Filter.builder()
				.name("미결 이슈")
				.explain(null)
				.jira(jira)
				.account(account)
				.build();
		this.filterRepository.save(filter);
		FilterDone filterDone = FilterDone.builder()
				.filter(filter)
				.isCompleted(0)
				.build();
		this.filterDoneRepository.save(filterDone);
	}
	public void defaultDoneIssue(Jira jira, Account account) {
		Filter filter = Filter.builder()
				.name("완료된 이슈")
				.explain(null)
				.jira(jira)
				.account(account)
				.build();
		this.filterRepository.save(filter);
		FilterDone filterDone = FilterDone.builder()
				.filter(filter)
				.isCompleted(1)
				.build();
		this.filterDoneRepository.save(filterDone);
	}
	public void defaultRecentlyCreated(Jira jira, Account account) {
		Filter filter = Filter.builder()
				.name("최근에 만듦")
				.explain(null)
				.jira(jira)
				.account(account)
				.build();
		this.filterRepository.save(filter);
		FilterIssueCreateDate filterIssueCreateDate = FilterIssueCreateDate.builder()
				.filter(filter)
				.startDate(null)
				.endDate(null)
				.BeforeDate(7)
				.build();
		this.filterIssueCreateDateRepository.save(filterIssueCreateDate);
	}
	public void defaultRecentlyDone(Jira jira, Account account) {
		Filter filter = Filter.builder()
				.name("최근에 해결됨")
				.explain(null)
				.jira(jira)
				.account(account)
				.build();
		this.filterRepository.save(filter);
		FilterDoneDate filterDoneDate = FilterDoneDate.builder()
				.filter(filter)
				.startDate(null)
				.endDate(null)
				.beforeDate(7)
				.build();
		this.filterDoneDateRepository.save(filterDoneDate);
	}
	public void defaultRecentlyUpdate(Jira jira, Account account) {
		Filter filter = Filter.builder()
				.name("최근에 업데이트")
				.explain(null)
				.jira(jira)
				.account(account)
				.build();
		this.filterRepository.save(filter);
		FilterIssueUpdate filterIssueUpdate = FilterIssueUpdate.builder()
				.filter(filter)
				.startDate(null)
				.endDate(null)
				.BeforeDate(7)
				.build();
		this.filterIssueUpdateRepository.save(filterIssueUpdate);
	}
	
	
// 기본 필터 생성 기능 --------------------------------------------------------------------------------------------
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
	public void filterCreateDateCreate(Filter filter,LocalDateTime startDate, LocalDateTime endDate, Integer beforeDate) {
		FilterIssueCreateDate filterIssueCreateDate = FilterIssueCreateDate.builder()
				.filter(filter)
				.startDate(startDate)
				.endDate(endDate)
				.BeforeDate(beforeDate)
				.build();
		this.filterIssueCreateDateRepository.save(filterIssueCreateDate);
	}
	public void filterIssueUpdateCreate(Filter filter, LocalDateTime startDate, LocalDateTime endDate, Integer beforeDate) {
		FilterIssueUpdate filterIssueUpdate = FilterIssueUpdate.builder()
				.filter(filter)
				.startDate(startDate)
				.endDate(endDate)
				.BeforeDate(beforeDate)
				.build();
		this.filterIssueUpdateRepository.save(filterIssueUpdate);
	}
	public void filterIssuePriorityCreate(Filter filter, IssuePriority issuePriority) {
		FilterIssuePriority filterIssuePriority = FilterIssuePriority.builder()
				.filter(filter)
				.issuePriority(issuePriority)
				.build();
		this.filterIssuePriorityRepository.save(filterIssuePriority);
	}
	public void filterIssueStatusCreate(Filter filter, IssueStatus issueStatus) {
		FilterIssueStatus filterIssueStatus = FilterIssueStatus.builder()
				.filter(filter)
				.issueStatus(issueStatus)
				.build();
		this.filterIssueStatusRepository.save(filterIssueStatus);
	}
	public void filterIssueTypeCreate(Filter filter,IssueType issueType) {
		FilterIssueType filterIssueType = FilterIssueType.builder()
				.filter(filter)
				.issutype(issueType)
				.build();
		this.filterIssueTypeRepository.save(filterIssueType);
	}
	public void filterManagerCreate(Filter filter, Account account) {
		FilterManager filterManager = FilterManager.builder()
				.filter(filter)
				.account(account)
				.build();
		this.filterManagerRepository.save(filterManager);
	}
	public void filterProjectCreate(Filter filter, Project project) {
		FilterProject filterProject = FilterProject.builder()
				.filter(filter)
				.project(project)
				.build();
		this.filterProjectRepository.save(filterProject);
	}
	public void filterReporterCreate(Filter filter, Account account) {
		FilterReporter filterReporter = FilterReporter.builder()
				.filter(filter)
				.account(account)
				.build();
		this.filterReporterRepostiory.save(filterReporter);
	}
	public void filterAuthCreate(Filter filter) {
		FilterAuth auth = FilterAuth.builder()
				.filter(filter)
				.build();
		this.filterAuthRepository.save(auth);
	}
	public void filterAuthProjectCreate(Filter filter, Project project, Integer type) {
		FilterAuth auth = FilterAuth.builder()
				.filter(filter)
				.project(project)
				.type(type)
				.build();
		this.filterAuthRepository.save(auth);
	}
	public void filterAuthUserCreate(Filter filter, Account account, Integer type) {
		FilterAuth auth = FilterAuth.builder()
				.filter(filter)
				.account(account)
				.type(type)
				.build();
		this.filterAuthRepository.save(auth);
	}
	public void filterAuthTeamCreate(Filter filter, Team team, Integer type) {
		FilterAuth auth = FilterAuth.builder()
				.filter(filter)
				.team(team)
				.type(type)
				.build();
		this.filterAuthRepository.save(auth);
	}
	public void filterDelete(Integer idx) {
		this.filterRepository.deleteById(idx);
	}
	public void filterLikeAdd(Filter filter, Account account) {
		FilterLikeMembers filterLikeMembers = FilterLikeMembers.builder()
				.account(account)
				.filter(filter)
				.build();
		this.filterLikeMembersRepository.save(filterLikeMembers);
	}
	public void filterLikeDelete(Integer idx) {
		this.filterLikeMembersRepository.deleteById(idx);
	}
	
	public void filterRecentClickedAddOrUpdate(Integer filterIdx, Integer accountIdx) {
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		Filter filter = filterRepository.findById(filterIdx).get();
		Account account = accountRepository.findById(accountIdx).get();
		Jira jira = jiraRepository.findById(jiraIdx).get();
		FilterRecentClicked filterRecentUpdate = filterRecentClickedRepository.findByFilterIdxAndAccountIdx(filterIdx, accountIdx);
		FilterRecentClicked filterRecentClicked = FilterRecentClicked.builder()
																	 .filter(filter)
																	 .account(account)
																	 .jira(jira)
																	 .build();
		if(filterRecentUpdate != null) {
			filterRecentUpdate.updateDate();
			this.filterRecentClickedRepository.save(filterRecentUpdate);
		}else {
			this.filterRecentClickedRepository.save(filterRecentClicked);
		}
				
	}
}
