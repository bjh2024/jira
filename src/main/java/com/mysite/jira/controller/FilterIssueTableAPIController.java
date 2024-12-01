package com.mysite.jira.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.jira.dto.FilterIssueDTO;
import com.mysite.jira.entity.Issue;
import com.mysite.jira.entity.Project;
import com.mysite.jira.repository.IssueRepository;
import com.mysite.jira.service.FilterIssueService;
import com.mysite.jira.service.IssueService;
import com.mysite.jira.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/filter_issue_table")
public class FilterIssueTableAPIController {

	private final FilterIssueService filterIssueService; 
	private final IssueService issueService;
	private final ProjectService projectService;
	
	@PostMapping("/project_filter")
	public List<FilterIssueDTO> getInputDatas(@RequestBody Integer[] projectIdx){
		List<Project> ProjectList = projectService.getProjectByJiraIdx(1);
		if(projectIdx.length == 0) {
			projectIdx = new Integer[ProjectList.size()];
			for(int i = 0; i < ProjectList.size(); i++) {
				projectIdx[i] = ProjectList.get(i).getIdx();
			}
		}
		List<Issue> issueByProjectIdx = filterIssueService.getIssueByProjectIdxIn(projectIdx);
		List<FilterIssueDTO> filterIssue = new ArrayList<>();
 		for (int i = 0; i < issueByProjectIdx.size(); i++) {
 			FilterIssueDTO dto = FilterIssueDTO.builder()
 				    .issueIconFilename(issueByProjectIdx.get(i).getIssueType().getIconFilename())
 				    .issueKey(issueByProjectIdx.get(i).getKey())
 				    .issueName(issueByProjectIdx.get(i).getName())
 				    .issueManagerIconFilename(issueByProjectIdx.get(i).getManager() != null ? 
 				                                issueByProjectIdx.get(i).getManager().getIconFilename() : "default_icon_file.png")
 				    .issueManagerName(issueByProjectIdx.get(i).getManager() != null ? 
 				                      issueByProjectIdx.get(i).getManager().getName() : "할당되지 않음")
 				    .issueReporterIconFilename(issueByProjectIdx.get(i).getReporter().getIconFilename())
 				    .issueReporterName(issueByProjectIdx.get(i).getReporter().getName())
 				    .issuePriorityIconFilename(issueByProjectIdx.get(i).getIssuePriority().getIconFilename())
 				    .issuePriorityName(issueByProjectIdx.get(i).getIssuePriority().getName())
 				    .issueStatusName(issueByProjectIdx.get(i).getIssueStatus().getName())
 				    .issueStatus(issueByProjectIdx.get(i).getIssueStatus().getStatus())
 				    .createDate(issueByProjectIdx.get(i).getCreateDate())
 				    .editDate(issueByProjectIdx.get(i).getEditDate())
 				    .deadlineDate(issueByProjectIdx.get(i).getDeadlineDate()) // 기본값을 현재 날짜로 설정
 				    .build();
 			filterIssue.add(dto);
		}
		return filterIssue;
	}
}
