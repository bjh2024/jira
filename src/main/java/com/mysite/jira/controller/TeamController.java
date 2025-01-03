package com.mysite.jira.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.jira.service.JiraMembersService;
import com.mysite.jira.service.TeamService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {
	
	private final TeamService teamService;
	
	private final JiraMembersService jiraMembersService;
	
	private final HttpSession session;
	
	@GetMapping("list")
	public String listPage(Model model, Principal principal) {
		Integer jiraIdx = (Integer)session.getAttribute("jiraIdx");
		model.addAttribute("memberList", jiraMembersService.getMembersByJiraIdx(jiraIdx));
		model.addAttribute("teamList", teamService.getTeamListByJiraIdx(jiraIdx));
		return "team/team_list";
	}
	
}
