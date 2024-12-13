package com.mysite.jira.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.ChartDTO;
import com.mysite.jira.entity.Jira;
import com.mysite.jira.entity.Project;
import com.mysite.jira.service.IssueService;
import com.mysite.jira.service.JiraService;
import com.mysite.jira.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/summation")
public class SummationAPIController {

	private final IssueService issueService;
	
	private final JiraService jiraService;
	
	private final ProjectService projectService;
	
	@GetMapping("/status_chart")
	public List<ChartDTO> getIssueStatusChart(@RequestParam("uri") String uri){
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		Integer jiraIdx = jira.getIdx();
		
		// 현재 위치한 프로젝트 페이지
		Project project = projectService.getByJiraIdxAndKeyProject(jiraIdx, uri.split("/")[3]);
		Integer projectIdx = project.getIdx();
		return issueService.getStatusChartDTO(projectIdx);
	}
	
	@GetMapping("/priority_chart")
	public List<ChartDTO> getIssuePriorityChart(@RequestParam("uri") String uri){
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		Integer jiraIdx = jira.getIdx();
		
		// 현재 위치한 프로젝트 페이지
		Project project = projectService.getByJiraIdxAndKeyProject(jiraIdx, uri.split("/")[3]);
		Integer projectIdx = project.getIdx();
		return issueService.getPriorityChartDTO(projectIdx);
	}
}
