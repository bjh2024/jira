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
import com.mysite.jira.entity.Account;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.service.AccountService;
import com.mysite.jira.service.JiraService;
import com.mysite.jira.service.TeamService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class TeamAPIController {

	private final TeamService teamService;
	
	private final AccountService accountService;

	private final JiraService jiraService;
	
	private final HttpSession session;
	
	@GetMapping("dashboard/list")
	public List<TeamListDTO> getTeamList(){
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		return teamService.getTeamListByJiraIdxDashboard(jiraIdx);
	}
	
	@GetMapping("duplication/name")
	public Integer getDuplicationTeamName(@RequestParam("teamName") String teamName){
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		if(teamService.getByJiraIdxAndNameTeam(jiraIdx, teamName) == null) {
			return 0;
		}
		return 1;
	}
	
	@PostMapping("create")
	public boolean createTeam(@RequestBody String teamName, Principal principal){
		
		Account account = accountService.getAccountByEmail(principal.getName());
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		Jira jira = jiraService.getByIdx(jiraIdx);
		return teamService.createTeam(account, jira, teamName);
	}
}
