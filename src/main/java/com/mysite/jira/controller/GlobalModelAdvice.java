package com.mysite.jira.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

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
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.Project;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.FilterService;
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
	
	private final FilterService filterService;

	@ModelAttribute
	public void addHeaderAttributes(HttpServletRequest request, Model model, Principal principal) {
		String uri = request.getRequestURI();
		if (principal == null)
			return;
		if (uri.contains("/api"))
			return;
		// 현재 로그인한 계정 정보
		Account currentUser = this.accountService.getAccountByEmail(principal.getName());

		// 가져올 값들
		Integer accountIdx = currentUser.getIdx();
		// Integer accountIdx = 1;
		Integer jiraIdx = 1;

		// header

		// header null 처리 필요
		List<String> leaders = jiraService.getjiraLeaderList(accountIdx);
		List<Issue> issuesRecentList = recentService.getRecentIssueList(accountIdx, jiraIdx);
		List<Project> allProjectList = projectService.getProjectByJiraIdx(jiraIdx);
		List<Account> allAccountList = accountService.getAccountList(jiraIdx);
		List<HeaderAlaramLogDataDTO> alarmLogData = logDataService.getJiraLogData(jiraIdx);
		List<AllRecentDTO> allRecentList = recentService.getAllRecentList(accountIdx, jiraIdx,
				LocalDateTime.now().minusDays(30), LocalDateTime.now());

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
		List<Filter> filterList = filterService.getByAccountIdxAndJiraIdx(accountIdx, jiraIdx);
		
		// 특정 경로 (/project/create)에서는 공통 모델 속성 추가하지 않기
		if (!uri.contains("/project/create")) {
			// 현재 jiraName url에서 가져오기
			model.addAttribute("currentJira", uri.split("/")[1]);

			// header
			model.addAttribute("leaders", leaders);
			model.addAttribute("issuesRecentList", issuesRecentList);
			model.addAttribute("allProjectList", allProjectList);
			model.addAttribute("allAccountList", allAccountList);
			model.addAttribute("alarmLogData", alarmLogData);
			model.addAttribute("allRecentList", allRecentList);
			model.addAttribute("currentUser", currentUser);

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
			model.addAttribute("filterList", filterList);
		}
	}

	@ModelAttribute
	public void addProjectHeaderAttributes(HttpServletRequest request, Model model) {
		String uri = request.getRequestURI();
		if (uri.contains("/api"))
			return;
		if (uri.contains("/project")) {
			Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
			Project project = projectService.getByJiraIdxAndKeyProject(jira.getIdx(), uri.split("/")[3]);
			model.addAttribute("project", project);
			model.addAttribute("currentJira", uri.split("/")[1]);
		}

	}

}
