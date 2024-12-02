package com.mysite.jira.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueLabelData;
import com.mysite.jira.entity.IssueType;
import com.mysite.jira.service.BoardMainService;
import com.mysite.jira.service.IssueService;
import com.mysite.jira.service.LogDataService;
import com.mysite.jira.service.ProjectService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
	@Autowired
	private BoardMainService boardMainService;
	
	private final IssueService issueService;
	
	private final ProjectService projectService;
	
	private final LogDataService logDataService;
	
	@GetMapping("/summation")
	public String summationPage(Model model) {
		Integer accountIdx = 1;
		Integer jiraIdx = 1;
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
		// 관련 프로젝트
		model.addAttribute("relevantProjectList", projectService.getProjectByJiraIdx(jiraIdx));
		
		return "project/summation";
	}
	
	@GetMapping("/list")
	public String listPage(Model model, @RequestParam(value="page", defaultValue = "0") int page) {
		Integer accountIdx = 1;
		Integer jiraIdx = 1;
		model.addAttribute("projectListIsLike", projectService.getProjectListIsLike(accountIdx, jiraIdx, page));
		
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
		
		List<IssueType> issueTypeList = boardMainService.getIssueTypesByProjectIdxAndGrade(1, 1);
		model.addAttribute("issueTypeList", issueTypeList);
		
		LocalDateTime now = LocalDateTime.now();
		List<IssueLabelData> labelDataList = boardMainService.getLabelData();
		model.addAttribute("labelDataList", labelDataList);
		model.addAttribute("currentTime", now);
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
