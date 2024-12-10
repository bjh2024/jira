package com.mysite.jira.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.project.summation.chartDTO;
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
	
	@PostMapping("/status_chart")
	public List<chartDTO> getIssueStatusChart(@RequestBody Map<String, Object> postData){
		String uri = postData.get("uri").toString();
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		Integer jiraIdx = jira.getIdx();
		// 현재 위치한 프로젝트 페이지
		Project project = projectService.getByJiraIdxAndKeyProject(jiraIdx, uri.split("/")[3]);
		Integer projectIdx = project.getIdx();
		return issueService.getStatusChartDTO(projectIdx);
	}
	
	@PostMapping("/priority_chart")
	public List<chartDTO> getIssuePriorityChart(@RequestBody Map<String, Object> postData){
		String uri = postData.get("uri").toString();
		Jira jira = jiraService.getByNameJira(uri.split("/")[1]);
		Integer jiraIdx = jira.getIdx();
		// 현재 위치한 프로젝트 페이지
		Project project = projectService.getByJiraIdxAndKeyProject(jiraIdx, uri.split("/")[3]);
		Integer projectIdx = project.getIdx();
		return issueService.getPriorityChartDTO(projectIdx);
	}
}
