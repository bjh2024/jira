package com.mysite.jira.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.dashboard.create.TeamListDTO;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.service.JiraService;
import com.mysite.jira.service.TeamService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class TeamAPIController {

	private final TeamService teamService;
	
	private final JiraService jiraService;
	
	@GetMapping("dashboard/list")
	public List<TeamListDTO> getTeamList(@RequestParam("uri") String uri){
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		return teamService.getTeamListByJiraIdx(jira.getIdx());
	}
}
