package com.mysite.jira.service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mysite.jira.dto.AllRecentDTO;
import com.mysite.jira.dto.header.HeaderRecentIssueDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Dashboard;
import com.mysite.jira.entity.Filter;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.Project;
import com.mysite.jira.repository.AccountRepository;
import com.mysite.jira.repository.DashboardRepository;
import com.mysite.jira.repository.FilterRepository;
import com.mysite.jira.repository.IssueRepository;
import com.mysite.jira.repository.JiraRepository;
import com.mysite.jira.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecentService {
	
	private final AccountRepository accountRepository;
	
	private final JiraRepository jiraRepository;
	
	private final UtilityService utilityService;
	
	private final IssueRepository issueRepository;

	private final ProjectRepository projectRepository;

	private final DashboardRepository dashboardRepository;
	
	private final FilterRepository filterRepository;
	
	// 업데이트, 프로젝트, 담당자, 보고자, 이슈상태로 필터링된 issue List(Name, key, iconName) 고쳐야됨
	public List<HeaderRecentIssueDTO> getRecentIssueList(Integer jiraIdx, LocalDateTime startDate, LocalDateTime endDate,
		Integer[] projectIdxArr, Integer[] managerIdxArr, Boolean isReporter, Integer[] statusArr, Principal principal) {
		List<Issue> issues = issueRepository.findByJiraIdx(jiraIdx);
		if (statusArr != null && statusArr.length > 0) {
			issues = filterByIssueStatus(issues, statusArr);
		}

		if (isReporter != null) {
			issues = filterByIsReporter(issues, isReporter, principal);
		}

		if (projectIdxArr != null && projectIdxArr.length > 0) {
			issues = filterByProjectIdx(issues, projectIdxArr);
		}

		if (managerIdxArr != null && managerIdxArr.length > 0) {
			issues = filterByManagerIdx(issues, managerIdxArr);
		}

		if (startDate != null && endDate != null) {
			issues = filterByEditDate(issues, startDate, endDate);
		}
		
		List<HeaderRecentIssueDTO> result = new ArrayList<>();
		for (int i = 0; i < issues.size(); i++) {
			String elapsedComment = utilityService.getElapsedComment(issues.get(i).getEditDate());
			HeaderRecentIssueDTO dto = HeaderRecentIssueDTO.builder()
					.name(issues.get(i).getName())
					.key(issues.get(i).getKey()).iconFilename(issues.get(i)
					.getIssueType().getIconFilename())
					.projectName(issues.get(i).getProject().getName())
					.elapsedTime(elapsedComment)
					.build();
			result.add(dto);
		}
		return result;
	}
	
	public List<Issue> filterByIssueStatus(List<Issue> issues, Integer[] statusArr) {
		if(statusArr.length == 0) return issues;
		List<Issue> result = new ArrayList<>();
		for (int i = 0; i < issues.size(); i++) {
			for (int j = 0; j < statusArr.length; j++) {
				if (issues.get(i).getIssueStatus().getStatus() == statusArr[j]) {
					result.add(issues.get(i));
				}
			}
		}
		return result;
	}

	public List<Issue> filterByIsReporter(List<Issue> issues, Boolean isReporter, Principal principal) {
		if(!isReporter) return issues;
		
		List<Issue> result = new ArrayList<>();
		Account account = null;
		if(!accountRepository.findByEmail(principal.getName()).isEmpty())
			account = accountRepository.findByEmail(principal.getName()).get();
		if(isReporter) {
			for (int i = 0; i < issues.size(); i++) {
				if (issues.get(i).getReporter().getIdx() == account.getIdx()) {
					result.add(issues.get(i));
				}
			}
		}
		return result;
	}

	public List<Issue> filterByProjectIdx(List<Issue> issues, Integer[] projectIdxArr) {
		if(projectIdxArr.length == 0) return issues;
		
		List<Issue> result = new ArrayList<>();
		for (int i = 0; i < issues.size(); i++) {
			for (int j = 0; j < projectIdxArr.length; j++) {
				if (issues.get(i).getProject().getIdx() == projectIdxArr[j]) {
					result.add(issues.get(i));
				}
			}
		}
		return result;
	}

	public List<Issue> filterByManagerIdx(List<Issue> issues, Integer[] managerIdxArr) {
		if(managerIdxArr.length == 0) return issues;
		
		List<Issue> result = new ArrayList<>();
		for (int i = 0; i < issues.size(); i++) {
			for (int j = 0; j < managerIdxArr.length; j++) {
				if (issues.get(i).getManager() == null)
					continue;
				if (issues.get(i).getManager().getIdx() == managerIdxArr[j]) {
					result.add(issues.get(i));
				}
			}
		}
		return result;
	}

	public List<Issue> filterByEditDate(List<Issue> issues, LocalDateTime startDate, LocalDateTime endDate) {
		List<Issue> result = new ArrayList<>();
		for (int i = 0; i < issues.size(); i++) {
			if ((issues.get(i).getEditDate().isAfter(startDate) || issues.get(i).getEditDate().isEqual(startDate)) &&
				(issues.get(i).getEditDate().isBefore(endDate)|| issues.get(i).getEditDate().isEqual(endDate))) {
				result.add(issues.get(i));
			}
		}
		return result;
	}

	public List<AllRecentDTO> getAllRecentList(Integer accountIdx, Integer jiraIdx, LocalDateTime startDate, LocalDateTime endDate){
		List<Map<String, Object>> allRecentList = jiraRepository.findClickedDataOrderByDateDesc(accountIdx, jiraIdx, startDate, endDate);
		List<AllRecentDTO> result = new ArrayList<>();
		for(int i = 0; i < allRecentList.size(); i++) {
			String type = allRecentList.get(i).get("type").toString();
			Integer idx = (Integer)allRecentList.get(i).get("idx");
			String name = allRecentList.get(i).get("name").toString();
			String iconFilename = allRecentList.get(i).get("iconFilename").toString();
			String key ="";
			if(allRecentList.get(i).get("key") != null) {
				key = allRecentList.get(i).get("key").toString();
			}
			LocalDateTime clickedDate = utilityService.localDateTimeChange(allRecentList.get(i).get("clickedDate"));
			String elapsedTime = utilityService.getElapsedComment(clickedDate);
			AllRecentDTO dto = AllRecentDTO.builder()
										   .type(type)
										   .idx(idx)
										   .name(name)
										   .iconFilename(iconFilename)
										   .key(key)
										   .elapsedTime(elapsedTime)
					   					   .build();
			result.add(dto);
		}
		return result;
	}
	// jiraIdx 와 accountIdx에 대한 최근 project list(Name, key, iconName) end개 => 즐겨찾기를 제외
	public List<Project> getRecentProjectList(Integer accountIdx, Integer jiraIdx, int end) {
		List<Project> result = new ArrayList<>();
		List<Project> projectList = projectRepository
				.findByAccountIdxAndJiraIdxMinusLikeMembers(accountIdx,jiraIdx);
		if(projectList.size() < end) {
			end = projectList.size();
		}
		for(int i = 0; i < end; i++) {
			result.add(projectList.get(i));
		}
		return result;
	}
	
	// jiraIdx에 대한 최근 project list(Name, key, iconName) end개
	public List<Project> getRecentProjectList(Integer jiraIdx, int end) {
		List<Project> result = new ArrayList<>();
		List<Project> projectList = projectRepository
				.findByJiraIdxOrderByProjectClickedList_ClickedDateDesc(jiraIdx);
		if(projectList.size() < end) {
			end = projectList.size();
		}
		for(int i = 0; i < end; i++) {
			result.add(projectList.get(i));
		}
		return result;
	}
	
	// jiraIdx 와 accountIdx에 대한 최근 filter list(Name, key, iconName) end개  => 즐겨찾기를 제외
	public List<Filter> getRecentFilterList(Integer accountIdx, Integer jiraIdx, int end){
		List<Filter> result = new ArrayList<>();
		List<Filter> filterList = filterRepository
				.findByAccountIdxAndJiraIdxMinusLikeMembers(accountIdx, jiraIdx);
		if(filterList.size() < end) {
			end = filterList.size();
		}
		for(int i = 0; i < end; i++) {
			result.add(filterList.get(i));
		}
		return result;
	}
	
	// jiraIdx 와 accountIdx에 대한 최근 dashboard list(Name) end개 => 즐겨찾기를 제외
	public List<Dashboard> getRecentDashboardList(Integer accountIdx, Integer jiraIdx, int end) {
		List<Dashboard> result = new ArrayList<>();
		List<Dashboard> dashboardList = dashboardRepository
				.findByAccountIdxAndJiraIdxMinusLikeMembers(accountIdx, jiraIdx);
		if(dashboardList.size() < end) {
			end = dashboardList.size();
		}
		for(int i = 0; i < end; i++) {
			result.add(dashboardList.get(i));
		}
		return result;
	}
	
	// jiraIdx와 accountIdx 에 대한 최근 issue list
	public List<Issue> getRecentIssueList(Integer accountIdx, Integer jiraIdx) {
		// 생성일 5년
		LocalDateTime endDate = LocalDateTime.now();
		LocalDateTime startDate = endDate.minusYears(5);
			return issueRepository
					.findByIssueClickedList_AccountIdxAndJiraIdxAndCreateDateBetweenOrderByIssueClickedList_ClickedDateDesc(accountIdx, jiraIdx, startDate, endDate);
		}
	
	// 오늘 최근 프로젝트, 대시보드, 이슈, 필터
	public List<AllRecentDTO> getTodayAllRecentList(Integer accountIdx, Integer jiraIdx){
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.now();
		return this.getAllRecentList(accountIdx, jiraIdx, startDate, endDate);
	}
	
	// 어제
	public List<AllRecentDTO> getYesterdayAllRecentList(Integer accountIdx, Integer jiraIdx){
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);
		return this.getAllRecentList(accountIdx, jiraIdx, startDate, endDate);
	}
	
	// 이번주
	public List<AllRecentDTO> getWeekAllRecentList(Integer accountIdx, Integer jiraIdx){
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MAX);
		return this.getAllRecentList(accountIdx, jiraIdx, startDate, endDate);
	}
	
	// 이번달
	public List<AllRecentDTO> getMonthAllRecentList(Integer accountIdx, Integer jiraIdx){
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.now().minusDays(8), LocalTime.MIDNIGHT);
		return this.getAllRecentList(accountIdx, jiraIdx, startDate, endDate);
	}
	
	// 한달이상
	public List<AllRecentDTO> getMonthGreaterAllRecentList(Integer accountIdx, Integer jiraIdx){
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(365 * 2), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.now().minusDays(31), LocalTime.MIDNIGHT);
		return this.getAllRecentList(accountIdx, jiraIdx, startDate, endDate);
	}


}
