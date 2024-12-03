package com.mysite.jira.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.IssueStatus;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.IssueService;
import com.mysite.jira.service.JiraService;
import com.mysite.jira.service.LikeService;
import com.mysite.jira.service.ProjectService;
import com.mysite.jira.service.RecentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final AccountService accountService;

	private final JiraService jiraService;
	
	private final ProjectService projectService;
	
	private final IssueService issueService;
	
	private final RecentService recentService;

	private final LikeService likeService;
	
	@GetMapping("/")
	public String main(Principal principal) {
		String jiraName = "";
		if(principal == null) {
			return "redirect:/account/login";
		}
		Account account = accountService.getAccountByEmail(principal.getName());
		Integer accountIdx = account.getIdx();
		jiraName = jiraService.getRecentTop1Jira(accountIdx).getName();
		return "redirect:/" + jiraName;
	}
	
	
	@GetMapping("/{jiraName}")
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
		
		// 나에게 할당
		model.addAttribute("managerByIssue", issueService.getManagerByIssueStatusIn(jiraIdx, accountIdx));
		model.addAttribute("managerByIssueCount", issueService.getMangerByIssueStatusInCount(jiraIdx, accountIdx));
		
		// 별표 표시됨
		model.addAttribute("allLikeList", likeService.getAllLikeList(accountIdx, jiraIdx));
		
		return "my_work";
	}
	
}
