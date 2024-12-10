package com.mysite.jira.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.LikeAPIRequestDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.LikeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeAPIController {

	private final AccountService accountService;
	
	private final LikeService likeService;
	
	@PostMapping("project")
	public void updateLikeProject(Principal principal, @RequestBody LikeAPIRequestDTO likeRequest) {
		Account account = accountService.getAccountByEmail(principal.getName());
		Integer accountIdx = account.getIdx();
		likeService.updateLikeProjectMember(accountIdx, likeRequest.getIdx(), likeRequest.getIsLike());
	}
	
	@PostMapping("dashboard")
	public void updateLikeDashboard(Principal principal, @RequestBody LikeAPIRequestDTO likeRequest) {
		Account account = accountService.getAccountByEmail(principal.getName());
		Integer accountIdx = account.getIdx();
		likeService.updateLikeDashboardMember(accountIdx, likeRequest.getIdx(), likeRequest.getIsLike());
	}
	
	@PostMapping("filter")
	public void updateLikeFilter(Principal principal, @RequestBody LikeAPIRequestDTO likeRequest) {
		Account account = accountService.getAccountByEmail(principal.getName());
		Integer accountIdx = account.getIdx();
		likeService.updateLikeFilterMember(accountIdx, likeRequest.getIdx(), likeRequest.getIsLike());
	}
	
}
