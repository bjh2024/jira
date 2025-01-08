package com.mysite.jira.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.jira.entity.Account;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.DashboardService;
import com.mysite.jira.service.IssueService;
import com.mysite.jira.service.ProjectService;
import com.mysite.jira.service.TeamService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {
	
	private final AccountService accountService;
	
	private final DashboardService dashboardService;
	
	private final ProjectService projectService;
	
	private final IssueService issueService;
	
	private final TeamService teamService;
	
	private final HttpSession session;
	
	@GetMapping("list")
	public String listPage(Model model, 
						   Principal principal) {
		Account account = accountService.getAccountByEmail(principal.getName());
		Integer accountIdx = account.getIdx();
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		
		model.addAttribute("jiraByAccountList", accountService.getAccountList(jiraIdx));
		model.addAttribute("jiraByProjectList", projectService.getByJiraIdxProject(jiraIdx));
		model.addAttribute("jiraByGroupList", teamService.getByJiraIdx(jiraIdx));
		model.addAttribute("dashboardList", dashboardService.getDashboardList(accountIdx, jiraIdx));
		return "dashboard/dashboard_list";
	}
	
	@GetMapping("detail/{dashboardIdx}")
	public String detailPage(Model model,
							 @PathVariable("dashboardIdx") Integer dashboardIdx, 
							 Principal principal) {
		Account account = accountService.getAccountByEmail(principal.getName());
		Integer accountIdx = account.getIdx();
		
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		
		model.addAttribute("dashboardItemList", dashboardService.getDashboardDetail(dashboardIdx));
		model.addAttribute("allotIssueCount", issueService.getMangerByIssueStatusInCount(jiraIdx, accountIdx));
		return "dashboard/dashboard_detail";
	}
	
	@GetMapping("edit/{dashboardIdx}")
	public String detailEditPage(Model model, 
								 @PathVariable("dashboardIdx") Integer dashboardIdx,
								 Principal principal) {
		Account account = accountService.getAccountByEmail(principal.getName());
		Integer accountIdx = account.getIdx();
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		
		model.addAttribute("dashboardItemList", dashboardService.getDashboardDetail(dashboardIdx));
		model.addAttribute("allotIssueCount", issueService.getMangerByIssueStatusInCount(jiraIdx, accountIdx));
		return "dashboard/dashboard_edit";
	}
}
