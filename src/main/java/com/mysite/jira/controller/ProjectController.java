package com.mysite.jira.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.IssueExtends;
import com.mysite.jira.entity.IssueLabelData;
import com.mysite.jira.entity.IssueReply;
import com.mysite.jira.entity.IssueStatus;
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
		// 현재 시간용 변수
		LocalDateTime now = LocalDateTime.now();
		model.addAttribute("currentTime", now);
		
		// 프로젝트 별 상태 개수를 알기 위해 그룹화된 상태 리스트
		List<Object[]> statusList = boardMainService.getIssueStatusByProjectIdx(1);
		model.addAttribute("statusList", statusList);
		
		// 프로젝트 별 이슈 진행도 순으로 정렬된 상태 리스트
		List<IssueStatus> orderedStatusList = boardMainService.getIssueStatusByProjectIdxOrderByStatusAsc(1);
		model.addAttribute("orderedStatusList", orderedStatusList);
		
		// 프로젝트 별 이슈 리스트
		List<Issue> issueList = boardMainService.getIssuesByProjectIdx(1);
		model.addAttribute("issueList", issueList);
		
		// 프로젝트 별 서브 이슈 유형을 제외한 이슈 유형 리스트
		List<IssueType> issueTypeList = boardMainService.getIssueTypesByProjectIdxAndGrade(1, 1);
		model.addAttribute("issueTypeList", issueTypeList);
		
		// 전체 레이블 리스트
		List<IssueLabelData> labelDataList = boardMainService.getLabelData();
		model.addAttribute("labelDataList", labelDataList);
		
		// 전체 댓글 리스트
		List<IssueReply> replyList = boardMainService.getIssueReply();
		model.addAttribute("replyList", replyList);
		
		// 프로젝트 별 상속 관계 리스트
		List<IssueExtends> extendsList = boardMainService.getIssueExtendsByProjectIdx(1);
		model.addAttribute("extendsList", extendsList);
		
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
