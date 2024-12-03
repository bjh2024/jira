package com.mysite.jira.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mysite.jira.dto.AllRecentDTO;
import com.mysite.jira.dto.LikeContentDTO;
import com.mysite.jira.dto.header.HeaderAlaramLogDataDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Dashboard;
import com.mysite.jira.entity.Filter;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.Project;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.BoardMainService;
import com.mysite.jira.service.JiraService;
import com.mysite.jira.service.LikeService;
import com.mysite.jira.service.LogDataService;
import com.mysite.jira.service.ProjectService;
import com.mysite.jira.service.RecentService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
@Component
public class GlobalModelAdvice {

	private final AccountService accountService;
	
	private final JiraService jiraService;

	private final ProjectService projectService;

	private final LogDataService logDataService;
	
	private final RecentService recentService;
	
	private final LikeService likeService;
	
	@ModelAttribute
	public void addHeaderAttributes(HttpServletRequest request, Model model, Principal principal) {
		if(principal == null) return;
		String uri = request.getRequestURI(); 
		Account currentUser = this.accountService.getAccountByEmail(principal.getName());
		if(currentUser == null) return;
		// 가져올 값들
		Integer accountIdx = currentUser.getIdx();
		// Integer accountIdx = 1;
		Integer jiraIdx = 1;
		
		// header null 처리 필요
		List<String> leaders = jiraService.getjiraLeaderList(accountIdx);
		List<Issue> issuesRecentList = recentService.getRecentIssueList(accountIdx, jiraIdx);
		List<Project> allProjectList = projectService.getProjectByJiraIdx(jiraIdx);
		List<Account> allAccountList = accountService.getAccountList(jiraIdx);
		List<HeaderAlaramLogDataDTO> alarmLogData = logDataService.getJiraLogData(jiraIdx);
		List<AllRecentDTO> allRecentList = recentService.getAllRecentList(accountIdx, jiraIdx, LocalDateTime.now().minusDays(30),LocalDateTime.now());
		
		// aside null 처리 필요
		List<AllRecentDTO> todayRecentList = recentService.getTodayAllRecentList(accountIdx, jiraIdx);
		List<AllRecentDTO> yesterdayRecentList = recentService.getYesterdayAllRecentList(accountIdx, jiraIdx);
		List<AllRecentDTO> thisWeekRecentList = recentService.getWeekAllRecentList(accountIdx, jiraIdx);
		List<AllRecentDTO> monthRecentList = recentService.getMonthAllRecentList(accountIdx, jiraIdx);
		List<AllRecentDTO> monthGreaterRecentList = recentService.getMonthGreaterAllRecentList(accountIdx, jiraIdx);
		List<Project> projectRecentList = recentService.getRecentProjectList(accountIdx, jiraIdx, 3);
		List<Filter> filterRecentList = recentService.getRecentFilterList(accountIdx, jiraIdx, 3);
		List<Dashboard> dashboardRecentList = recentService.getRecentDashboardList(accountIdx, jiraIdx, 3);
		List<LikeContentDTO> allLikeMembers = likeService.getAllLikeList(accountIdx, jiraIdx);
		List<LikeContentDTO> projectLikeMembers = likeService.getProjectLikeList(accountIdx, jiraIdx);
		List<LikeContentDTO> filterLikeMembers = likeService.getFilterLikeList(accountIdx, jiraIdx);
		List<LikeContentDTO> dashboardLikeMembers = likeService.getDashboardLikeList(accountIdx, jiraIdx);
		// 특정 경로 (/project/create)에서는 공통 모델 속성 추가하지 않기
		if (!uri.contains("/project/create")) {
			// header
			model.addAttribute("leaders", leaders);
			model.addAttribute("issuesRecentList", issuesRecentList);
			model.addAttribute("allProjectList", allProjectList);
			model.addAttribute("allAccountList", allAccountList);
			model.addAttribute("alarmLogData", alarmLogData);
			model.addAttribute("allRecentList", allRecentList);
			
			// aside
			model.addAttribute("todayRecentList", todayRecentList);
			model.addAttribute("yesterdayRecentList", yesterdayRecentList);
			model.addAttribute("thisWeekRecentList", thisWeekRecentList);
			model.addAttribute("monthRecentList", monthRecentList);
			model.addAttribute("monthGreaterRecentList", monthGreaterRecentList);
			model.addAttribute("allLikeMembers", allLikeMembers);
			
			model.addAttribute("projectLikeMembers", projectLikeMembers);
			model.addAttribute("projectRecentList", projectRecentList);
			
			model.addAttribute("filterLikeMembers", filterLikeMembers);
			model.addAttribute("filterRecentList", filterRecentList);
			
			model.addAttribute("dashboardLikeMembers", dashboardLikeMembers);
			model.addAttribute("dashboardRecentList", dashboardRecentList);
			
			// 현재 접속 유저
			model.addAttribute("currentUser", currentUser);
		}
	}
	
	private final BoardMainService boardMainService;
	
	@ModelAttribute
	public void addProjectHeaderAttributes(HttpServletRequest request, Model model) {
		String uri = request.getRequestURI(); 
		Integer projectIdx = 1;
		Project project = boardMainService.getProjectNameById(projectIdx);
		
		if(uri.contains("/project")) {
			model.addAttribute("project", project);
		}
		
	}
	
}
