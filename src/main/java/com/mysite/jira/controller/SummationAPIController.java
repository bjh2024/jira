package com.mysite.jira.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.ChartDTO;
import com.mysite.jira.service.IssueService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/summation")
public class SummationAPIController {

	private final IssueService issueService;
	
	private final HttpSession session;
	
	@GetMapping("/status_chart")
	public List<ChartDTO> getIssueStatusChart(){
		Integer projectIdx = (Integer)session.getAttribute("projectIdx");
		return issueService.getStatusChartDTO(projectIdx);
	}
	
	@GetMapping("/priority_chart")
	public List<ChartDTO> getIssuePriorityChart(){
		Integer projectIdx = (Integer)session.getAttribute("projectIdx");
		return issueService.getPriorityChartDTO(projectIdx);
	}
}
