package com.mysite.jira.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.dashboard.create.TeamListDTO;
import com.mysite.jira.dto.team.TeamCreateDTO;
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.JiraService;
import com.mysite.jira.service.TeamService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class TeamAPIController {

	private final TeamService teamService;
	
	private final AccountService accountService;

	private final JiraService jiraService;
	
	@GetMapping("dashboard/list")
	public List<TeamListDTO> getTeamList(@RequestParam("uri") String uri){
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		return teamService.getTeamListByJiraIdxDashboard(jira.getIdx());
	}
	
	@GetMapping("duplication/name")
	public Integer getDuplicationTeamName(@RequestParam("teamName") String teamName,
										  @RequestParam("uri") String uri){
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		if(teamService.getByJiraIdxAndNameTeam(jira.getIdx(), teamName) == null) {
			return 0;
		}
		return 1;
	}
	
	@PostMapping("create")
	public boolean createTeam(@RequestBody TeamCreateDTO teamCreateDTO, Principal principal){
		String teamName = teamCreateDTO.getName();
		String uri = teamCreateDTO.getUri();
		
		Account account = accountService.getAccountByEmail(principal.getName());
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		return teamService.createTeam(account, jira, teamName);
	}
}
