package com.mysite.jira.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.LikeContentDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.JiraService;
import com.mysite.jira.service.LikeService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aside")
public class AsideAPIController {

	private final LikeService likeService;
	
	private final AccountService accountService;
	
	private final JiraService jiraService;
	
	@GetMapping("/like/filter")
	public List<LikeContentDTO> getLikeFilter(HttpServletRequest request, Principal principal, @RequestParam("uri") String uri){
		// 현재 로그인 계정
		Account account = accountService.getAccountByEmail(principal.getName());
		Integer accountIdx = account.getIdx();
		// 현재 위치한 지라 페이지
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		Integer jiraIdx = jira.getIdx();
		return likeService.getFilterLikeList(accountIdx, jiraIdx);
	}
	
	@GetMapping("/like/dashboard")
	public List<LikeContentDTO> getLikeDashboard(HttpServletRequest request, Principal principal, @RequestParam("uri") String uri){
		// 현재 로그인 계정
		Account account = accountService.getAccountByEmail(principal.getName());
		Integer accountIdx = account.getIdx();
		// 현재 위치한 지라 페이지
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		Integer jiraIdx = jira.getIdx();
		
		return likeService.getDashboardLikeList(accountIdx, jiraIdx);
	}
	
	@GetMapping("/like/project")
	public List<LikeContentDTO> getLikeProject(HttpServletRequest request, Principal principal, @RequestParam("uri") String uri){
		// 현재 로그인 계정
		Account account = accountService.getAccountByEmail(principal.getName());
		Integer accountIdx = account.getIdx();
		// 현재 위치한 지라 페이지
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		Integer jiraIdx = jira.getIdx();
		
		return likeService.getProjectLikeList(accountIdx, jiraIdx);
	}
	
}
