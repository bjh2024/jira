package com.mysite.jira.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mysite.jira.service.IssueService;
import com.mysite.jira.service.LogDataService;
import com.mysite.jira.service.ProjectService;
import com.mysite.jira.service.RecentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MyWorkController {
	
	private final ProjectService projectService;
	
	private final IssueService issueService;
	
	private final RecentService recentService;
	
	@GetMapping("/")
	public String filter(Model model) {
		Integer jiraIdx = 1;
		Integer accountIdx = 1;
		model.addAttribute("projectList", projectService.getProjectList(accountIdx, jiraIdx));
		
		// 작업
		model.addAttribute("issueLogDateTodayList", issueService.getTodayIssueByBetweenCreateDateDesc(jiraIdx));
		model.addAttribute("issueLogDateYesterdayList", issueService.getYesterdayIssueByBetweenCreateDateDesc(jiraIdx));
		model.addAttribute("issueLogDateWeekList", issueService.getWeekIssueByBetweenCreateDateDesc(jiraIdx));
		model.addAttribute("issueLogDateMonthList", issueService.getMonthIssueByBetweenCreateDateDesc(jiraIdx));
		model.addAttribute("issueLogDateMonthGreaterList", issueService.getMonthGreaterIssueByBetweenCreateDateDesc(jiraIdx));
		
		// 확인
		model.addAttribute("recentTodayList", recentService.getTodayAllRecentList(accountIdx, jiraIdx));
		model.addAttribute("recentYesterdayList", recentService.getYesterdayAllRecentList(accountIdx, jiraIdx));
		model.addAttribute("recentWeekList", recentService.getWeekAllRecentList(accountIdx, jiraIdx));
		model.addAttribute("recentMonthList", recentService.getMonthAllRecentList(accountIdx, jiraIdx));
		model.addAttribute("recentMonthGreaterList", recentService.getMonthGreaterAllRecentList(accountIdx, jiraIdx));
		
		
		
		return "my_work";
	}
	
}
