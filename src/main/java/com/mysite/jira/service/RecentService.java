package com.mysite.jira.service;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysite.jira.dto.AllRecentDTO;
import com.mysite.jira.dto.header.HeaderRecentIssueDTO;
import com.mysite.jira.entity.Dashboard;
import com.mysite.jira.entity.Filter;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.Project;
import com.mysite.jira.repository.DashboardRepository;
import com.mysite.jira.repository.FilterRepository;
import com.mysite.jira.repository.IssueRepository;
import com.mysite.jira.repository.JiraRepository;
import com.mysite.jira.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecentService {
	
	private final JiraRepository jiraRepository;
	
	private final ObjectMapper objectMapper;
	
	private final UtilityService utilityService;
	
	private final IssueRepository issueRepository;

	private final ProjectRepository projectRepository;

	private final DashboardRepository dashboardRepository;
	
	private final FilterRepository filterRepository;
	
	// 업데이트, 프로젝트, 담당자, 보고자, 이슈상태로 필터링된 issue List(Name, key, iconName)
	public List<HeaderRecentIssueDTO> getRecentIssueList(Integer jiraIdx, LocalDateTime startDate, LocalDateTime endDate,
		Integer[] projectIdxArr, Integer[] managerIdxArr, Integer reporterIdx, Integer[] statusArr) {
		List<Issue> issues = issueRepository.findByJiraIdx(jiraIdx);

		if (statusArr != null && statusArr.length > 0) {
			issues = filterByIssueStatus(issues, statusArr);
		}

		if (reporterIdx != null) {
			issues = filterByReporterIdx(issues, reporterIdx);
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

	public List<Issue> filterByReporterIdx(List<Issue> issues, Integer reporterIdx) {
		List<Issue> result = new ArrayList<>();
		for (int i = 0; i < issues.size(); i++) {
			if (issues.get(i).getReporter().getIdx() == reporterIdx) {
				result.add(issues.get(i));
			}
		}
		return result;
	}

	public List<Issue> filterByProjectIdx(List<Issue> issues, Integer[] projectIdxArr) {
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
		try {
			for(int i = 0; i < allRecentList.size(); i++) {
				String name = "";
				String iconFilename = "";
				Timestamp clickedDate = null;
				String elapsedTime = "";
				if(allRecentList.get(i).get("name") != null && allRecentList.get(i).get("name") instanceof String) {
					name = allRecentList.get(i).get("name").toString();
				}
				if(allRecentList.get(i).get("iconFilename") != null && allRecentList.get(i).get("iconFilename") instanceof String) {
					iconFilename = allRecentList.get(i).get("iconFilename").toString();
				}
				if(allRecentList.get(i).get("clickedDate") != null && allRecentList.get(i).get("clickedDate") instanceof Timestamp) {
					clickedDate = objectMapper.convertValue(allRecentList.get(i).get("clickedDate"), Timestamp.class);
					elapsedTime = utilityService.getElapsedComment(clickedDate.toLocalDateTime());
				}
				AllRecentDTO dto = AllRecentDTO.builder()
											   .name(name)
											   .iconFilename(iconFilename)
											   .elapsedTime(elapsedTime)
											   .build();
				result.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// jiraIdx 와 accountIdx에 대한 최근 project list(Name, key, iconName) end개
	public List<Project> getRecentProjectList(Integer jiraIdx, Integer accountIdx, int end) {
		List<Project> result = new ArrayList<>();
		List<Project> projectList = projectRepository
				.findByProjectClickedList_AccountIdxAndJiraIdxOrderByProjectClickedList_ClickedDateDesc(accountIdx,jiraIdx);
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
	
	// jiraIdx 와 accountIdx에 대한 최근 filter list(Name, key, iconName) end개
	public List<Filter> getRecentFilterList(Integer jiraIdx, Integer accountIdx, int end){
		List<Filter> result = new ArrayList<>();
		List<Filter> filterList = filterRepository
				.findByFilterClickedList_AccountIdxAndJiraIdxOrderByFilterClickedList_ClickedDateDesc(accountIdx, jiraIdx);
		if(filterList.size() < end) {
			end = filterList.size();
		}
		for(int i = 0; i < end; i++) {
			result.add(filterList.get(i));
		}
		return result;
	}
	
	// jiraIdx 와 accountIdx에 대한 최근 dashboard list(Name) end개
	public List<Dashboard> getRecentDashboardList(Integer jiraIdx, Integer accountIdx, int end) {
		List<Dashboard> result = new ArrayList<>();
		List<Dashboard> dashboardList = dashboardRepository
				.findByDashClickedList_AccountIdxAndJiraIdxOrderByDashClickedList_ClickedDateDesc(accountIdx, jiraIdx);
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
			return issueRepository
					.findByIssueClickedList_AccountIdxAndJiraIdxOrderByIssueClickedList_ClickedDateDesc(accountIdx, jiraIdx);
		}
	
	// 오늘 최근 프로젝트, 대시보드, 이슈, 필터
	public List<AllRecentDTO> getTodayAllRecentList(Integer accountIdx, Integer jiraIdx){
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.now();
		List<Map<String, Object>> allRecentList = jiraRepository.findClickedDataOrderByDateDesc(accountIdx, jiraIdx, startDate, endDate);
		
		List<AllRecentDTO> result = new ArrayList<>();
		try {
			for(int i = 0; i < allRecentList.size(); i++) {
				String name = "";
				String iconFilename = "";
				LocalDateTime clickedDate = null;
				String elapsedTime = "";
				if(allRecentList.get(i).get("name") != null) {
					name = allRecentList.get(i).get("name").toString();
				}
				if(allRecentList.get(i).get("iconFilename") != null) {
					iconFilename = allRecentList.get(i).get("iconFilename").toString();
				}
				clickedDate = utilityService.localDateTimeChange(allRecentList.get(i).get("clickedDate"));
				if(clickedDate != null) {
					elapsedTime = utilityService.getElapsedComment(clickedDate);
				}
				AllRecentDTO dto = AllRecentDTO.builder()
											   .name(name)
											   .iconFilename(iconFilename)
											   .elapsedTime(elapsedTime)
											   .build();
				result.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 어제
	public List<AllRecentDTO> getYesterdayAllRecentList(Integer accountIdx, Integer jiraIdx){
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);
		
		List<Map<String, Object>> allRecentList = jiraRepository.findClickedDataOrderByDateDesc(accountIdx, jiraIdx, startDate, endDate);
		List<AllRecentDTO> result = new ArrayList<>();
		try {
			for(int i = 0; i < allRecentList.size(); i++) {
				String name = "";
				String iconFilename = "";
				LocalDateTime clickedDate = null;
				String elapsedTime = "";
				if(allRecentList.get(i).get("name") != null) {
					name = allRecentList.get(i).get("name").toString();
				}
				if(allRecentList.get(i).get("iconFilename") != null) {
					iconFilename = allRecentList.get(i).get("iconFilename").toString();
				}
				clickedDate = utilityService.localDateTimeChange(allRecentList.get(i).get("clickedDate"));
				if(clickedDate != null) {
					elapsedTime = utilityService.getElapsedComment(clickedDate);
				}
				AllRecentDTO dto = AllRecentDTO.builder()
											   .name(name)
											   .iconFilename(iconFilename)
											   .elapsedTime(elapsedTime)
											   .build();
				result.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 이번주
	public List<AllRecentDTO> getWeekAllRecentList(Integer accountIdx, Integer jiraIdx){
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MAX);
		
		List<Map<String, Object>> allRecentList = jiraRepository.findClickedDataOrderByDateDesc(accountIdx, jiraIdx, startDate, endDate);
		List<AllRecentDTO> result = new ArrayList<>();
		try {
			for(int i = 0; i < allRecentList.size(); i++) {
				String name = "";
				String iconFilename = "";
				LocalDateTime clickedDate = null;
				String elapsedTime = "";
				if(allRecentList.get(i).get("name") != null) {
					name = allRecentList.get(i).get("name").toString();
				}
				if(allRecentList.get(i).get("iconFilename") != null) {
					iconFilename = allRecentList.get(i).get("iconFilename").toString();
				}
				clickedDate = utilityService.localDateTimeChange(allRecentList.get(i).get("clickedDate"));
				if(clickedDate != null) {
					elapsedTime = utilityService.getElapsedComment(clickedDate);
				}
				AllRecentDTO dto = AllRecentDTO.builder()
											   .name(name)
											   .iconFilename(iconFilename)
											   .elapsedTime(elapsedTime)
											   .build();
				result.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 이번달
	public List<AllRecentDTO> getMonthAllRecentList(Integer accountIdx, Integer jiraIdx){
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.now().minusDays(8), LocalTime.MIDNIGHT);
		List<Map<String, Object>> allRecentList = jiraRepository.findClickedDataOrderByDateDesc(accountIdx, jiraIdx, startDate, endDate);
		List<AllRecentDTO> result = new ArrayList<>();
		try {
			for(int i = 0; i < allRecentList.size(); i++) {
				String name = "";
				String iconFilename = "";
				LocalDateTime clickedDate = null;
				String elapsedTime = "";
				if(allRecentList.get(i).get("name") != null) {
					name = allRecentList.get(i).get("name").toString();
				}
				if(allRecentList.get(i).get("iconFilename") != null) {
					iconFilename = allRecentList.get(i).get("iconFilename").toString();
				}
				clickedDate = utilityService.localDateTimeChange(allRecentList.get(i).get("clickedDate"));
				if(clickedDate != null) {
					elapsedTime = utilityService.getElapsedComment(clickedDate);
				}
				AllRecentDTO dto = AllRecentDTO.builder()
											   .name(name)
											   .iconFilename(iconFilename)
											   .elapsedTime(elapsedTime)
											   .build();
				result.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 한달이상
	public List<AllRecentDTO> getMonthGreaterAllRecentList(Integer accountIdx, Integer jiraIdx){
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(365 * 2), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.now().minusDays(31), LocalTime.MIDNIGHT);
		
		List<Map<String, Object>> allRecentList = jiraRepository.findClickedDataOrderByDateDesc(accountIdx, jiraIdx, startDate, endDate);
		List<AllRecentDTO> result = new ArrayList<>();
		try {
			for(int i = 0; i < allRecentList.size(); i++) {
				String name = "";
				String iconFilename = "";
				LocalDateTime clickedDate = null;
				String elapsedTime = "";
				if(allRecentList.get(i).get("name") != null) {
					name = allRecentList.get(i).get("name").toString();
				}
				if(allRecentList.get(i).get("iconFilename") != null) {
					iconFilename = allRecentList.get(i).get("iconFilename").toString();
				}
				clickedDate = utilityService.localDateTimeChange(allRecentList.get(i).get("clickedDate"));
				if(clickedDate != null) {
					elapsedTime = utilityService.getElapsedComment(clickedDate);
				}
				AllRecentDTO dto = AllRecentDTO.builder()
											   .name(name)
											   .iconFilename(iconFilename)
											   .elapsedTime(elapsedTime)
											   .build();
				result.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}


}
