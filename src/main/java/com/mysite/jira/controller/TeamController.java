package com.mysite.jira.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.jira.entity.Jira;
import com.mysite.jira.service.JiraMembersService;
import com.mysite.jira.service.JiraService;
import com.mysite.jira.service.TeamService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/{jiraName}/team")
public class TeamController {
	
	private final JiraService jiraService;
	
	private final TeamService teamService;
	
	private final JiraMembersService jiraMembersService;
	
	@GetMapping("list")
	public String listPage(Model model, Principal principal, HttpServletRequest request) {
		String uri = request.getRequestURI();
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		model.addAttribute("memberList", jiraMembersService.getMembersByJiraIdx(jira.getIdx()));
		model.addAttribute("teamList", teamService.getTeamListByJiraIdx(jira.getIdx()));
		return "team/team_list";
	}
}
