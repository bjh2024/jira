package com.mysite.jira.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.project.summation.chartDTO;
import com.mysite.jira.service.IssueService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/summation")
public class SummationAPIController {

	private final IssueService issueService;
	
	@GetMapping("/status_chart")
	public List<chartDTO> getIssueStatusChart(){
		Integer projectIdx = 1;
		return issueService.getStatusChartDTO(projectIdx);
	}
	
	@GetMapping("/priority_chart")
	public List<chartDTO> getIssuePriorityChart(){
		Integer projectIdx = 1;
		return issueService.getPriorityChartDTO(projectIdx);
	}
}
