package com.mysite.jira.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.LikeContentDTO;
import com.mysite.jira.service.LikeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aside")
public class AsideAPIController {

	private final LikeService likeService;
	
	@GetMapping("/like/filter")
	public List<LikeContentDTO> getLikeFilter(){
		Integer accountIdx = 1;
		Integer jiraIdx = 1;
		return likeService.getFilterLikeList(accountIdx, jiraIdx);
	}
	
	@GetMapping("/like/dashboard")
	public List<LikeContentDTO> getLikeDashboard(){
		Integer accountIdx = 1;
		Integer jiraIdx = 1;
		return likeService.getDashboardLikeList(accountIdx, jiraIdx);
	}
	
	@GetMapping("/like/project")
	public List<LikeContentDTO> getLikeProject(){
		Integer accountIdx = 1;
		Integer jiraIdx = 1;
		return likeService.getProjectLikeList(accountIdx, jiraIdx);
	}
	
}
