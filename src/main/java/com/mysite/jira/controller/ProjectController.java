package com.mysite.jira.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.ProjectLogData;
import com.mysite.jira.service.BoardMainService;
import com.mysite.jira.service.IssueService;
import com.mysite.jira.service.LogDataService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
	@Autowired
	private BoardMainService boardMainService;
	
	private final IssueService issueService;
	
	private final LogDataService logDataService;
	
	@GetMapping("/summation")
	public String summationPage(Model model) {
		Integer accountIdx = 1;
		Integer projectIdx = 1;
		model.addAttribute("createIssueCount", issueService.getSevenDayCreateIssueCount(projectIdx));
		model.addAttribute("complementIssueCount", issueService.getSevenDayComplementIssueCount(projectIdx));
		model.addAttribute("updateIssueCount", issueService.getSevenDayUpdateIssueCount(projectIdx));
		// 상태 개요
		model.addAttribute("deadlineIssueCount", issueService.getSevenDayDeadlineIssueCount(projectIdx));
		// 최근 활동
		model.addAttribute("projectLogData", logDataService.getProjectLogData(projectIdx));
		// 우선순위 분석
		model.addAttribute("taskTypeData", issueService.getTaskTypeDTO(projectIdx));
		// 작업 유형
		model.addAttribute("sumTaskTypeData", issueService.getSumTaskTypeDTO(projectIdx));
		// 팀 워크로드
		model.addAttribute("managerCountData", issueService.getManagerIssueCount(projectIdx));
		
		return "project/summation";
	}
	
	@GetMapping("/list")
	public String listPage() {
		return "project/project_list";
	}
	
	@GetMapping("/create")
	public String createPage() {
		return "project/project_create";
	}
	
	@GetMapping("/board_main")
	public String boardMain(Model model) {
		List<Object[]> statusList = boardMainService.getIssueStatusByProjectIdx(1);
		model.addAttribute("statusList", statusList);
		List<Issue> issueList = boardMainService.getIssuesByProjectIdx(1);
		model.addAttribute("issueList", issueList);
		return "project/board_main";
	}
	
	@GetMapping("/setting_issue_type")
	public String settingIssueType() {
		return "project/setting_issue_type";
	}
	
	@GetMapping("/attached_files")
	public String attachedFiles() {
		return "project/attached_files";
	}
	
	@GetMapping("/chart")
	public String chart() {
		return "project/chart";
	}
}
