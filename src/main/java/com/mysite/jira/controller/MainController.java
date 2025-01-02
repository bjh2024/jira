package com.mysite.jira.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.IssueService;
import com.mysite.jira.service.JiraService;
import com.mysite.jira.service.LikeService;
import com.mysite.jira.service.ProjectService;
import com.mysite.jira.service.RecentService;

import jakarta.servlet.http.HttpSession;
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

	private final HttpSession session;
	
	@GetMapping("/")
	public String filter(Model model, Principal principal) {
		if(principal == null) {
			return "redirect:/account/login";
		}
		Account account = new Account();
		if(principal.getName().split("@").length < 2) {
			Account accKakao = this.accountService.getAccountByKakaoKey(principal.getName());
			Account accNaver = this.accountService.getAccountByNaverKey(principal.getName());
			account = accKakao != null ? accKakao : accNaver;
		}else {
			account = this.accountService.getAccountByEmail(principal.getName());
		}
		
		Integer accountIdx = account.getIdx();
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
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
	// /favicon.ico GlobalModelAdvice에 들어갈때 오류
	@GetMapping("/favicon.ico")
    public ResponseEntity<Void> favicon() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
