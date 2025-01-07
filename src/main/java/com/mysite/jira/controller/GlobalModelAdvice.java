package com.mysite.jira.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.jira.dto.AllRecentDTO;
import com.mysite.jira.dto.LikeContentDTO;
import com.mysite.jira.dto.header.HeaderAlaramLogDataDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Dashboard;
import com.mysite.jira.entity.Filter;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.JiraMembers;
import com.mysite.jira.entity.Project;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.DashboardService;
import com.mysite.jira.service.FilterService;
import com.mysite.jira.service.IssueTypeService;
import com.mysite.jira.service.JiraMembersService;
import com.mysite.jira.service.JiraService;
import com.mysite.jira.service.LikeService;
import com.mysite.jira.service.LogDataService;
import com.mysite.jira.service.ProjectService;
import com.mysite.jira.service.RecentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
@Component
public class GlobalModelAdvice {

	private final AccountService accountService;

	private final JiraService jiraService;
	
	private final JiraMembersService jiraMembersService;

	private final ProjectService projectService;

	private final LogDataService logDataService;

	private final RecentService recentService;

	private final LikeService likeService;
	
	private final IssueTypeService issueTypeService;
	
	private final FilterService filterService;

	private final DashboardService dashboardService;

	private final HttpSession session;
	
	@ModelAttribute
	public void addProjectHeaderAttributes(HttpServletRequest request, Model model, Principal principal) {
		String uri = request.getRequestURI();
		if (uri.contains("/api") || uri.contains("/project/create") || uri.contains("/project/list") || uri.contains("/project/profile"))
			return;
		if (uri.contains("/project")) {
			Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
			Jira jira = jiraService.getByIdx(jiraIdx);
			List<JiraMembers> jiraMemberList = jiraService.getJiraMemberListByJiraIdx(jiraIdx);
			
			Project project = projectService.getByJiraIdxAndKeyProject(jiraIdx, uri.split("/")[2]);
			session.setAttribute("projectIdx", project.getIdx());
			List<Account> memberAccList = projectService.getProjectMemberListByProjectIdx(project.getIdx());
			
			Account account = accountService.getAccountByEmail(principal.getName());
			projectService.addProjectRecentClicked(account, jira, project);
			
			model.addAttribute("project", project);
			model.addAttribute("jiraMemberList", jiraMemberList);
			model.addAttribute("memberAccList", memberAccList);
		}
	}
	
	@ModelAttribute
	public void addDashboardHeaderAttributes(HttpServletRequest request, 
											 Model model, 
											 Principal principal,
											 @PathVariable(value = "dashboardIdx", required = false) Integer dashboardIdx) {
		String uri = request.getRequestURI();
		if (uri.contains("/api") || uri.contains("/dashboard/list")) return;
		if(uri.contains("/dashboard")) {
			boolean isDetail = uri.contains("/detail") ? true : false;
			Dashboard dashboard = dashboardService.getDashboardByIdx(dashboardIdx);
			
			Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
			Jira jira = jiraService.getByIdx(jiraIdx);
			
			Account account = accountService.getAccountByEmail(principal.getName());
			 
			dashboardService.addDashboardRecentClicked(dashboard, jira, account);
			model.addAttribute("isDetail", isDetail);
			model.addAttribute("currentDashboard", dashboard);
		}
	}
	
	@ModelAttribute
	public void updateOrAddFilterRecent(HttpServletRequest request, @RequestParam(value= "filter", required = false) Integer filterIdx,
			Principal principal) {
		String uri = request.getRequestURI();
		if(uri.contains("/account"))return;
		Account accountIdx = accountService.getAccountByEmail(principal.getName());
		if(uri.contains("/filter")) {
			if(filterIdx != null) {
				System.out.println("Global filter");
				filterService.filterRecentClickedAddOrUpdate(filterIdx, accountIdx.getIdx());
			}
		}
	}
	
	@ModelAttribute
	public void addHeaderAttributes(HttpServletRequest request, Model model, Principal principal) {
		String uri = request.getRequestURI();
		if(principal == null ||
		   uri.length() == 0 ||
		   uri.contains("/api")||
		   uri.contains("/error") ||
		   uri.contains("/account") ||
		   uri.equals("/favicon.ico")) return;
		// 특정 경로 /project/create에서는 공통 모델 속성 추가하지 않기
		if (uri.contains("/project/create")) return;
		try {
			// 현재 로그인한 계정 정보
			Account currentUser = new Account();
			if(principal.getName().split("@").length < 2) {
				Account accKakao = this.accountService.getAccountByKakaoKey(principal.getName());
				Account accNaver = this.accountService.getAccountByNaverKey(principal.getName());
				currentUser = accKakao != null ? accKakao : accNaver;
			}else {
				currentUser = this.accountService.getAccountByEmail(principal.getName());
			}
		    
			// 가져올 값들
			Integer accountIdx = currentUser.getIdx();
			Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
			
			// 현재 jira
			Jira jira = jiraService.getByIdx(jiraIdx);
			model.addAttribute("currentJira", jira);
			
			// header
			List<JiraMembers> accountByjiraMemberList = jiraService.getJiraByAccountIdxList(accountIdx);
			List<Issue> issuesRecentList = recentService.getRecentIssueList(accountIdx, jiraIdx);
			List<Project> allProjectList = projectService.getProjectByJiraIdx(jiraIdx);
			List<Account> allAccountList = accountService.getAccountList(jiraIdx);
			List<HeaderAlaramLogDataDTO> alarmLogData = logDataService.getJiraLogData(jiraIdx);
			List<AllRecentDTO> allRecentList = recentService.getAllRecentList(accountIdx, jiraIdx, LocalDateTime.now().minusDays(30),LocalDateTime.now());
			
			// aside
			List<AllRecentDTO> todayRecentList = recentService.getTodayAllRecentList(accountIdx, jiraIdx);
			
			List<AllRecentDTO> yesterdayRecentList = recentService.getYesterdayAllRecentList(accountIdx, jiraIdx);
			
			List<AllRecentDTO> thisWeekRecentList = recentService.getWeekAllRecentList(accountIdx, jiraIdx);
			
			List<AllRecentDTO> monthRecentList = recentService.getMonthAllRecentList(accountIdx, jiraIdx);
			
			List<AllRecentDTO> monthGreaterRecentList = recentService.getMonthGreaterAllRecentList(accountIdx, jiraIdx);
			
			System.out.println("Global aside recent");
			List<Project> projectRecentList = recentService.getRecentProjectList(accountIdx, jiraIdx, 3);
			List<Filter> filterRecentList = recentService.getRecentFilterList(accountIdx, jiraIdx, 3);
			List<Dashboard> dashboardRecentList = recentService.getRecentDashboardList(accountIdx, jiraIdx, 3);
			List<LikeContentDTO> allLikeMembers = likeService.getAllLikeList(accountIdx, jiraIdx);
			List<LikeContentDTO> projectLikeMembers = likeService.getProjectLikeList(accountIdx, jiraIdx);
			List<LikeContentDTO> filterLikeMembers = likeService.getFilterLikeList(accountIdx, jiraIdx);
			List<LikeContentDTO> dashboardLikeMembers = likeService.getDashboardLikeList(accountIdx, jiraIdx);
			
			List<Filter> filterList = filterService.getByAccountIdxAndJiraIdx(accountIdx, jiraIdx);
			
			// chat
			List<JiraMembers> allJiraMembersList = jiraMembersService.getMembersByJiraIdx(jiraIdx);
			
			// header
			model.addAttribute("accountByjiraMemberList", accountByjiraMemberList);
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
			
			// chat
			model.addAttribute("allJiraMemberList", allJiraMembersList);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	
	@ModelAttribute
	public void settingAsideAttributes(HttpServletRequest request, 
									   Model model,
									   @PathVariable(value = "jiraName", required = false) String jiraName,
									   @PathVariable(value = "projectKey", required = false) String projectKey) {
		String uri = request.getRequestURI();
		if (uri.contains("/api")) return;
		if(uri.contains("/setting")) {
			Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
			model.addAttribute("currentProject", projectService.getByJiraIdxAndKeyProject(jiraIdx, projectKey));

			Project project = projectService.getByJiraIdxAndKeyProject(jiraIdx, projectKey);
			Integer projectIdx = project.getIdx();
			model.addAttribute("projectMemberList", projectService.getProjectMembersByProjectIdx(projectIdx));
			
			model.addAttribute("issueTypeInfoList", issueTypeService.getByProjectIdxIssueTypeList(projectIdx));
		}
	}
	
}
