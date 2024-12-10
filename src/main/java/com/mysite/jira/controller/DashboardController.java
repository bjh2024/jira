package com.mysite.jira.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.DashboardService;
import com.mysite.jira.service.JiraService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/{jiraName}/dashboard")
public class DashboardController {
	
	private final AccountService accountService;
	
	private final JiraService jiraService;
	
	private final DashboardService dashboardService;
	
	@GetMapping("list")
	public String listPage(Model model, 
						   @PathVariable("jiraName") String jiraName, 
						   Principal principal) {
		Account account = accountService.getAccountByEmail(principal.getName());
		Jira jira = jiraService.getByNameJira(jiraName);
		model.addAttribute("dashboardList", dashboardService.getDashboardList(account.getIdx(), jira.getIdx()));
		return "dashboard/dashboard_list";
	}
	@GetMapping("detail/{dashboardIdx}")
	public String detailPage(HttpServletRequest request, Model model, @PathVariable("dashboardIdx") Integer dashboardIdx) {
	
		model.addAttribute("dashboardItemList", dashboardService.getDashboardDetail(dashboardIdx));
		return "dashboard/dashboard_detail";
	}
	@GetMapping("edit/{dashboardIdx}")
	public String detailEditPage(HttpServletRequest request, Model model, @PathVariable("dashboardIdx") Integer dashboardIdx) {
		
		
		return "dashboard/dashboard_edit";
	}
}
