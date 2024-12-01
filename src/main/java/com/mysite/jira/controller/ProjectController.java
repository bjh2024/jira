package com.mysite.jira.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueLabelData;
import com.mysite.jira.entity.IssueType;
import com.mysite.jira.service.BoardMainService;

@Controller
@RequestMapping("/project")
public class ProjectController {
	@Autowired
	private BoardMainService boardMainService;
	
	@GetMapping("/summation")
	public String summationPage() {
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
