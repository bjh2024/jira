package com.mysite.jira.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueType;
import com.mysite.jira.entity.ProjectLogData;
import com.mysite.jira.repository.IssueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {

	private final IssueRepository issueRepository;

	public List<Issue> getIssuesByJiraIdx(Integer jiraIdx) {
		return issueRepository.findByJiraIdx(jiraIdx);
	}

	// kdw 오늘
	public List<Issue> getTodayIssueByBetweenCreateDateDesc(Integer jiraIdx) {
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.now();
		List<Issue> issues = issueRepository.IssueByJiraIdxAndCreateDateBetweenOrderByCreateDateDesc(jiraIdx, startDate,
				endDate);
		System.out.println("오늘 : " + "startDate : " + startDate +", endDate : " + endDate);
		// ProjectLogData의 중복값 제거
		for (int i = 0; i < issues.size(); i++) {
			Set<String> setIconFiles = new HashSet<>();
			List<ProjectLogData> logDataList = new ArrayList<>();
			for (ProjectLogData logData : issues.get(i).getProjectLogDataList()) {
				if (setIconFiles.add(logData.getAccount().getIconFilename())) {
					logDataList.add(logData);
				}
			}
			issues.get(i).updateProjectLogDataList(logDataList);
		}
		return issues;
	}

	// kdw 어제
	public List<Issue> getYesterdayIssueByBetweenCreateDateDesc(Integer jiraIdx) {
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);
		
		List<Issue> issues = issueRepository.IssueByJiraIdxAndCreateDateBetweenOrderByCreateDateDesc(jiraIdx, startDate,
				endDate);

		// ProjectLogData의 중복값 제거
		for (int i = 0; i < issues.size(); i++) {
			Set<String> setIconFiles = new HashSet<>();
			List<ProjectLogData> logDataList = new ArrayList<>();
			for (ProjectLogData logData : issues.get(i).getProjectLogDataList()) {
				if (setIconFiles.add(logData.getAccount().getIconFilename())) {
					logDataList.add(logData);
				}
			}
			if(logDataList.size() != 0) {
				issues.get(i).updateProjectLogDataList(logDataList);
			}
		}
		return issues;
	}

	// kdw 지난주
	public List<Issue> getWeekIssueByBetweenCreateDateDesc(Integer jiraIdx) {
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MAX);
		
		List<Issue> issues = issueRepository.IssueByJiraIdxAndCreateDateBetweenOrderByCreateDateDesc(jiraIdx, startDate,
				endDate);
		// ProjectLogData의 중복값 제거
		for (int i = 0; i < issues.size(); i++) {
			Set<String> setIconFiles = new HashSet<>();
			List<ProjectLogData> logDataList = new ArrayList<>();
			for (ProjectLogData logData : issues.get(i).getProjectLogDataList()) {
				if (setIconFiles.add(logData.getAccount().getIconFilename())) {
					logDataList.add(logData);
				}
			}
			if(logDataList.size() != 0) {
				issues.get(i).updateProjectLogDataList(logDataList);
			}
		}
		return issues;
	}

	// kdw 지난달
	public List<Issue> getMonthIssueByBetweenCreateDateDesc(Integer jiraIdx) {
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.now().minusDays(8), LocalTime.MIDNIGHT);
		
		List<Issue> issues = issueRepository.IssueByJiraIdxAndCreateDateBetweenOrderByCreateDateDesc(jiraIdx, startDate,
				endDate);
		// ProjectLogData의 중복값 제거
		for (int i = 0; i < issues.size(); i++) {
			Set<String> setIconFiles = new HashSet<>();
			List<ProjectLogData> logDataList = new ArrayList<>();
			for (ProjectLogData logData : issues.get(i).getProjectLogDataList()) {
				if (setIconFiles.add(logData.getAccount().getIconFilename())) {
					logDataList.add(logData);
				}
			}
			if(logDataList.size() != 0) {
				issues.get(i).updateProjectLogDataList(logDataList);
			}
		}
		return issues;
	}

	// kdw 한달이상
	public List<Issue> getMonthGreaterIssueByBetweenCreateDateDesc(Integer jiraIdx) {
		LocalDateTime startDate = LocalDateTime.of(LocalDate.now().minusDays(365 * 2), LocalTime.MIDNIGHT);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.now().minusDays(31), LocalTime.MIDNIGHT);
		
		List<Issue> issues = issueRepository.IssueByJiraIdxAndCreateDateBetweenOrderByCreateDateDesc(jiraIdx, startDate,
				endDate);
		// ProjectLogData의 중복값 제거
		for (int i = 0; i < issues.size(); i++) {
			Set<String> setIconFiles = new HashSet<>();
			List<ProjectLogData> logDataList = new ArrayList<>();
			for (ProjectLogData logData : issues.get(i).getProjectLogDataList()) {
				if (setIconFiles.add(logData.getAccount().getIconFilename())) {
					logDataList.add(logData);
				}
			}
			if(logDataList.size() != 0) {
				issues.get(i).updateProjectLogDataList(logDataList);
			}
		}
		return issues;
	}

}
